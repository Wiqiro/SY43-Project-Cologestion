from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends, HTTPException
from requests import Session
from sqlalchemy.orm.exc import NoResultFound

router = APIRouter(prefix="/events", tags=["Events"])


@router.get("/house_share/{house_share_id}", response_model=List[schemas.Event])
def get_house_share_events(house_share_id: int, db: Session = Depends(get_db)):
    try:
        return (
            db.query(models.Event)
            .filter(models.Event.house_share_id == house_share_id)
            .all()
        )
    except NoResultFound:
        raise HTTPException(status_code=404, detail="House share not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("", response_model=schemas.Event)
def add_event(event: schemas.EventCreate, db: Session = Depends(get_db)):
    try:
        new_event = models.Event(**event.model_dump())
        db.add(new_event)
        db.commit()
        db.refresh(new_event)
        return new_event
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.put("/{event_id}", response_model=schemas.Event)
def update_event(
    event_id: int, event: schemas.EventCreate, db: Session = Depends(get_db)
):
    try:
        new_event = event.model_dump()
        updated_rows = db.query(models.Event).filter_by(id=event_id).update(new_event)
        db.commit()
        if updated_rows == 0:
            raise HTTPException(status_code=404, detail="Event not found")
        return db.query(models.Event).get(event_id)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.delete("/{event_id}", response_model=schemas.Event)
def delete_event(event_id: int, db: Session = Depends(get_db)):
    try:
        event = db.query(models.Event).get(event_id)
        db.delete(event)
        db.commit()
        return event
    except NoResultFound:
        raise HTTPException(status_code=404, detail="Event not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
