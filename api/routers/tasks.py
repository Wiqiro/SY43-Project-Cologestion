from fastapi import APIRouter


router = APIRouter(prefix="/tasks", tags=["Tasks"])

@router.get("/user/{user_id}")
def get_user_tasks(user_id: int):
    return {}

@router.get("/houseshare/{houseshare_id}")
def get_houseshare_tasks(houseshare_id: int):
    return {}

@router.post("")
def add_task():
    return {}

@router.put("/{task_id}")
def update_task(task_id: int):
    return {}

@router.delete("/{task_id}")
def delete_task(task_id: int):
    return {}