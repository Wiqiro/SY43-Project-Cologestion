from typing import List

import models
import schemas
from database import get_db
from fastapi import APIRouter, Depends, HTTPException
from requests import Session
from sqlalchemy.orm.exc import NoResultFound

router = APIRouter(prefix="/tasks", tags=["Tasks"])


@router.get("/user/{user_id}", response_model=List[schemas.Task])
def get_user_tasks(user_id: int, db: Session = Depends(get_db)):
    try:
        return db.query(models.Task).filter(models.Task.assignee_id == user_id).all()
    except NoResultFound:
        raise HTTPException(status_code=404, detail="User not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get("/house_share/{house_share_id}", response_model=List[schemas.Task])
def get_house_share_tasks(house_share_id: int, db: Session = Depends(get_db)):
    try:
        return (
            db.query(models.Task)
            .filter(models.Task.house_share_id == house_share_id)
            .all()
        )
    except NoResultFound:
        raise HTTPException(status_code=404, detail="House share not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("", response_model=schemas.Task)
def add_task(task: schemas.TaskCreate, db: Session = Depends(get_db)):
    try:
        new_task = models.Task(**task.model_dump())
        db.add(new_task)
        db.commit()
        db.refresh(new_task)
        return new_task
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.put("/{task_id}", response_model=schemas.Task)
def update_task(task_id: int, task: schemas.TaskCreate, db: Session = Depends(get_db)):
    try:
        new_task = task.model_dump()
        updated_rows = db.query(models.Task).filter_by(id=task_id).update(new_task)
        db.commit()
        if updated_rows == 0:
            raise HTTPException(status_code=404, detail="Task not found")
        return db.query(models.Task).get(task_id)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.delete("/{task_id}", response_model=schemas.Task)
def delete_task(task_id: int, db: Session = Depends(get_db)):
    try:
        task = db.query(models.Task).get(task_id)
        db.delete(task)
        db.commit()
        return task
    except NoResultFound:
        raise HTTPException(status_code=404, detail="Task not found")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
