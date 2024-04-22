import models
from database import get_db
from fastapi import APIRouter, Depends
from requests import Session

router = APIRouter(prefix="/events", tags=["Events"])


@router.get("/house_share/{house_share_id}")
def get_house_share_events(house_share_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.Event)
        .filter(models.Event.house_share_id == house_share_id)
        .all()
    )


@router.post("")
def add_event(db: Session = Depends(get_db)):
    return {}


@router.put("/{event_id}")
def update_event(event_id: int, db: Session = Depends(get_db)):
    return {}


@router.delete("/{event_id}")
def delete_event(event_id: int, db: Session = Depends(get_db)):
    return {}
