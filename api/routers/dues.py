from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends, HTTPException
from requests import Session
from sqlalchemy.orm.exc import NoResultFound

router = APIRouter(prefix="/dues", tags=["Dues"])


@router.get("/user/{user_id}", response_model=List[schemas.Due])
def get_user_dues(user_id: int, db: Session = Depends(get_db)):
    try:
        return (
            db.query(models.Due)
            .filter(
                (
                    (models.Due.debtor_id == user_id)
                    | (models.Due.creditor_id == user_id)
                )
            )
            .all()
        )
    except NoResultFound:
        raise HTTPException(status_code=404, detail="User not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get("/house_share/{house_share_id}", response_model=List[schemas.Due])
def get_house_share_dues(house_share_id: int, db: Session = Depends(get_db)):
    try:
        return (
            db.query(models.Due)
            .filter(models.Due.house_share_id == house_share_id)
            .all()
        )
    except NoResultFound:
        raise HTTPException(status_code=404, detail="House share not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("", response_model=schemas.Due)
def add_due(due: schemas.DueCreate, db: Session = Depends(get_db)):
    try:
        if due.creditor_id == due.debtor_id:
            raise HTTPException(
                status_code=422, detail="Creditor and debtor cannot be the same"
            )

        new_due = models.Due(**due.model_dump())
        db.add(new_due)
        db.commit()
        db.refresh(new_due)
        return new_due
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.put("/{due_id}", response_model=schemas.Due)
def update_due(due_id: int, due: schemas.DueCreate, db: Session = Depends(get_db)):
    try:
        new_due = due.model_dump()
        updated_rows = db.query(models.Due).filter_by(id=due_id).update(new_due)
        db.commit()
        if updated_rows == 0:
            raise HTTPException(status_code=404, detail="Due not found")
        return db.query(models.Due).get(due_id)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.delete("/{due_id}", response_model=schemas.Due)
def delete_due(due_id: int, db: Session = Depends(get_db)):
    try:
        due = db.query(models.Due).get(due_id)
        db.delete(due)
        db.commit()
        return due
    except NoResultFound:
        raise HTTPException(status_code=404, detail="Due not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
