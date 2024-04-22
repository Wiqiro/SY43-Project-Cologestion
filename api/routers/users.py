from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends
from requests import Session

router = APIRouter(prefix="/users", tags=["Users"])


@router.get("/{user_id}")
def get_user(user_id: int, db: Session = Depends(get_db)):
    return db.query(models.User).get(user_id)


@router.get("/house_share/{house_share_id}", response_model=List[schemas.User])
def get_house_share_users(house_share_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.User)
        .join(
            models.HouseShareMember, models.User.id == models.HouseShareMember.user_id
        )
        .filter(models.HouseShareMember.house_share_id == house_share_id)
        .all()
    )
