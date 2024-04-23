from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends
from requests import Session

router = APIRouter(prefix="/events", tags=["Events"])


@router.get("/house_share/{house_share_id}", response_model=List[schemas.Event])
def get_house_share_events(house_share_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.Event)
        .filter(models.Event.house_share_id == house_share_id)
        .all()
    )


@router.post("", response_model=schemas.Event)
def add_event(event: schemas.EventCreate, db: Session = Depends(get_db)):
    new_event = models.Event(**event.model_dump())
    db.add(new_event)
    db.commit()
    db.refresh(new_event)
    return new_event


@router.put("/{event_id}", response_model=schemas.Event)
def update_event(
    event_id: int, event: schemas.EventCreate, db: Session = Depends(get_db)
):
    return {}


@router.delete("/{event_id}", response_model=schemas.Event)
def delete_event(event_id: int, db: Session = Depends(get_db)):
    event = db.query(models.Event).get(event_id)
    db.delete(event)
    db.commit()
    return event
