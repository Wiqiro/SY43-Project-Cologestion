from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends
from requests import Session
from sqlalchemy import or_

router = APIRouter(prefix="/dues", tags=["Dues"])


@router.get("/user/{user_id}", response_model=List[schemas.Due])
def get_user_dues(user_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.Due)
        .filter(or_(models.Due.debtor_id == user_id, models.Due.creditor_id == user_id))
        .all()
    )


@router.get("/house_share/{house_share_id}", response_model=List[schemas.Due])
def get_house_share_dues(house_share_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.Due).filter(models.Due.house_share_id == house_share_id).all()
    )


@router.post("", response_model=schemas.Due)
def add_due(due: schemas.DueCreate, db: Session = Depends(get_db)):
    new_due = models.Due(**due.model_dump())
    db.add(new_due)
    db.commit()
    db.refresh(new_due)
    return new_due


@router.put("/{due_id}", response_model=schemas.Due)
def update_due(due_id: int, due: schemas.DueCreate, db: Session = Depends(get_db)):
    return {}


@router.delete("/{due_id}", response_model=schemas.Due)
def delete_due(due_id: int, db: Session = Depends(get_db)):
    due = db.query(models.Due).get(due_id)
    db.delete(due)
    db.commit()
    return due
