from fastapi import APIRouter


router = APIRouter(prefix="/dues", tags=["Dues"])

@router.get("/user/{user_id}")
def get_user_dues(user_id: int):
    return {}

@router.get("/houseshare/{houseshare_id}")
def get_houseshare_dues(houseshare_id: int):
    return {}

@router.post("")
def add_due():
    return {}

@router.put("/{due_id}")
def update_due(due_id: int):
    return {}

@router.delete("/{due_id}")
def delete_due(due_id: int):
    return {}