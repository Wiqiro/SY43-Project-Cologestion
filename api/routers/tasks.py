from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends
from requests import Session

router = APIRouter(prefix="/tasks", tags=["Tasks"])


@router.get("/user/{user_id}", response_model=List[schemas.Task])
def get_user_tasks(user_id: int, db: Session = Depends(get_db)):
    return db.query(models.Task).filter(models.Task.assignee_id == user_id).all()


@router.get("/house_share/{house_share_id}", response_model=List[schemas.Task])
def get_house_share_tasks(house_share_id: int, db: Session = Depends(get_db)):
    return (
        db.query(models.Task).filter(models.Task.house_share_id == house_share_id).all()
    )


@router.post("", response_model=schemas.Task)
def add_task(task: schemas.TaskCreate, db: Session = Depends(get_db)):
    new_task = models.Task(**task.model_dump())
    db.add(new_task)
    db.commit()
    db.refresh(new_task)
    return new_task


@router.put("/{task_id}", response_model=schemas.Task)
def update_task(task_id: int, task: schemas.TaskCreate, db: Session = Depends(get_db)):
    return {}


@router.delete("/{task_id}", response_model=schemas.Task)
def delete_task(task_id: int, db: Session = Depends(get_db)):
    task = db.query(models.Task).get(task_id)
    db.delete(task)
    db.commit()
    return task
