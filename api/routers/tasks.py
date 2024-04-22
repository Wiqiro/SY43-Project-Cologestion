import models
from database import get_db
from fastapi import APIRouter, Depends
from requests import Session

router = APIRouter(prefix="/tasks", tags=["Tasks"])


@router.get("/user/{user_id}")
def get_user_tasks(user_id: int, db: Session = Depends(get_db)):
    return db.query(models.Task).filter(models.Task.user_id == user_id).all()


@router.get("/house_share/{house_share_id}")
def get_house_share_tasks(house_share_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.Task).filter(models.Task.house_share_id == house_share_id).all()
    )


@router.post("")
def add_task(db: Session = Depends(get_db)):
    return {}


@router.put("/{task_id}")
def update_task(task_id: int, db: Session = Depends(get_db)):
    return {}


@router.delete("/{task_id}")
def delete_task(task_id: int, db: Session = Depends(get_db)):
    return {}
