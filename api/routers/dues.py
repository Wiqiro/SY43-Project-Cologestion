import models
from database import get_db
from fastapi import APIRouter, Depends
from requests import Session

router = APIRouter(prefix="/dues", tags=["Dues"])


@router.get("/user/{user_id}")
def get_user_dues(user_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.Due)
        .filter(models.Due.debtor_id == user_id or models.Due.creditor_id == user_id)
        .all()
    )


@router.get("/house_share/{house_share_id}")
def get_house_share_dues(house_share_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.Due).filter(models.Due.house_share_id == house_share_id).all()
    )


@router.post("")
def add_due(db: Session = Depends(get_db)):
    return {}


@router.put("/{due_id}")
def update_due(due_id: int, db: Session = Depends(get_db)):
    return {}


@router.delete("/{due_id}")
def delete_due(due_id: int, db: Session = Depends(get_db)):
    return {}
