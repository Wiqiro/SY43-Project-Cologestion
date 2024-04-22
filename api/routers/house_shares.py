from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends
from requests import Session

router = APIRouter(prefix="/house_shares", tags=["House Shares"])


@router.get("/{house_share_id}", response_model=List[schemas.HouseShare])
def get_house_share(house_share_id: int, db: Session = Depends(get_db)):
    return db.query(models.HouseShare).get(house_share_id)


@router.get("/user/{user_id}", response_model=List[schemas.HouseShare])
def get_user_house_shares(user_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.HouseShare)
        .join(
            models.HouseShareMember,
            models.HouseShare.id == models.HouseShareMember.house_share_id,
        )
        .filter(models.HouseShareMember.user_id == user_id)
        .all()
    )


@router.post("", response_model=schemas.HouseShare)
def add_house_share(
    house_share: schemas.HouseShareCreate, db: Session = Depends(get_db)
):
    return {}


@router.put("/{house_share_id}", response_model=schemas.HouseShare)
def update_house_share(
    house_share_id: int,
    house_share: schemas.HouseShareCreate,
    db: Session = Depends(get_db),
):
    return {}


@router.delete("/{house_share_id}", response_model=schemas.HouseShare)
def delete_house_share(house_share_id: int, db: Session = Depends(get_db)):
    return {}
