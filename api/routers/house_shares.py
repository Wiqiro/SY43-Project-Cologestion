import datetime
import time
from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends, HTTPException
from requests import Session
from sqlalchemy.orm.exc import NoResultFound
from utils import get_current_user

router = APIRouter(prefix="/house_shares", tags=["House Shares"])


@router.get("/{house_share_id}", response_model=schemas.HouseShare)
def get_house_share(
    house_share_id: int,
    db: Session = Depends(get_db),
    current_user_id: int = Depends(get_current_user),
):
    try:
        return db.query(models.HouseShare).get(house_share_id)
    except NoResultFound:
        raise HTTPException(status_code=404, detail="House share not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get("/user/{user_id}", response_model=List[schemas.HouseShare])
def get_user_house_shares(
    user_id: int,
    db: Session = Depends(get_db),
    current_user_id: int = Depends(get_current_user),
):
    try:
        return (
            db.query(models.HouseShare)
            .join(
                models.HouseShareMember,
                models.HouseShare.id == models.HouseShareMember.house_share_id,
            )
            .filter(models.HouseShareMember.user_id == user_id)
            .all()
        )
    except NoResultFound:
        raise HTTPException(status_code=404, detail="User not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("", response_model=schemas.HouseShare)
def add_house_share(
    house_share: schemas.HouseShareCreate,
    db: Session = Depends(get_db),
    current_user_id: int = Depends(get_current_user),
):
    try:
        new_house_share = models.HouseShare(**house_share.model_dump())
        new_house_share.creation_date = time.time()
        db.add(new_house_share)
        db.commit()
        db.refresh(new_house_share)
        return new_house_share
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.put("/{house_share_id}", response_model=schemas.HouseShare)
def update_house_share(
    house_share_id: int,
    house_share: schemas.HouseShareCreate,
    db: Session = Depends(get_db),
    current_user_id: int = Depends(get_current_user),
):
    try:
        new_house_share = house_share.model_dump()
        updated_rows = (
            db.query(models.HouseShare)
            .filter_by(id=house_share_id)
            .update(new_house_share)
        )
        db.commit()
        if updated_rows == 0:
            raise HTTPException(status_code=404, detail="House share not found")
        return db.query(models.HouseShare).get(house_share_id)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.delete("/{house_share_id}", response_model=schemas.HouseShare)
def delete_house_share(
    house_share_id: int,
    db: Session = Depends(get_db),
    current_user_id: int = Depends(get_current_user),
):
    try:
        house_share = db.query(models.HouseShare).get(house_share_id)
        db.delete(house_share)
        db.commit()
        return house_share
    except NoResultFound:
        raise HTTPException(status_code=404, detail="House share not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
