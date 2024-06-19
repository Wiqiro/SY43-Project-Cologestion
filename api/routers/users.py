from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends, HTTPException
from requests import Session
from sqlalchemy.orm.exc import NoResultFound
from utils import get_current_user, get_password_hash, verify_password

router = APIRouter(prefix="/users", tags=["Users"])


@router.get("/me", response_model=schemas.User)
def get_me(
    db: Session = Depends(get_db), current_user_id: int = Depends(get_current_user)
):
    try:
        return db.query(models.User).get(current_user_id)
    except NoResultFound:
        raise HTTPException(status_code=404, detail="User not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get("/{user_id}", response_model=schemas.User)
def get_user(
    user_id: int,
    db: Session = Depends(get_db),
    current_user_id: int = Depends(get_current_user),
):
    try:
        return db.query(models.User).get(user_id)
    except NoResultFound:
        raise HTTPException(status_code=404, detail="User not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get("/house_share/{house_share_id}", response_model=List[schemas.User])
def get_house_share_users(
    house_share_id: int,
    db: Session = Depends(get_db),
    current_user_id: int = Depends(get_current_user),
):
    try:
        return (
            db.query(models.User)
            .join(
                models.HouseShareMember,
                models.User.id == models.HouseShareMember.user_id,
            )
            .filter(models.HouseShareMember.house_share_id == house_share_id)
            .all()
        )
    except NoResultFound:
        raise HTTPException(status_code=404, detail="House share not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("", response_model=schemas.User)
def add_user(user: schemas.UserCreate, db: Session = Depends(get_db)):
    try:
        user_dict = user.model_dump()
        password_hash = get_password_hash(user_dict.pop("password"))
        new_user = models.User(**user_dict, password_hash=password_hash)
        db.add(new_user)
        db.commit()
        db.refresh(new_user)
        return new_user
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.put("/me/password")
def change_password(
    passwords: schemas.UserPasswordChange,
    db: Session = Depends(get_db),
    current_user_id: int = Depends(get_current_user),
):
    try:
        user = db.query(models.User).get(current_user_id)
        if not verify_password(passwords.old_password, user.password_hash):
            raise HTTPException(status_code=401, detail="Incorrect password")
        user.password_hash = get_password_hash(passwords.new_password)
        db.commit()
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
