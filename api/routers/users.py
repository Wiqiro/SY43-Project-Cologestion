from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends, HTTPException
from requests import Session
from sqlalchemy.orm.exc import NoResultFound

router = APIRouter(prefix="/users", tags=["Users"])


@router.get("/{user_id}", response_model=schemas.User)
def get_user(user_id: int, db: Session = Depends(get_db)):
    try:
        return db.query(models.User).get(user_id)
    except NoResultFound:
        raise HTTPException(status_code=404, detail="User not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get("/house_share/{house_share_id}", response_model=List[schemas.User])
def get_house_share_users(house_share_id: int, db: Session = Depends(get_db)):
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
