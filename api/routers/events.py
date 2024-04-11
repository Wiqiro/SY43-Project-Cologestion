from fastapi import APIRouter


router = APIRouter(prefix="/events", tags=["Events"])

@router.get("/user/{user_id}")
def get_user_events(user_id: int):
    return {}

@router.get("/houseshare/{houseshare_id}")
def get_houseshare_events(houseshare_id: int):
    return {}

@router.post("")
def add_event():
    return {}

@router.put("/{event_id}")
def update_event(event_id: int):
    return {}

@router.delete("/{event_id}")
def delete_event(event_id: int):
    return {}
