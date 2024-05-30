from typing import List, Optional

from pydantic import BaseModel, field_validator


class HouseShareBase(BaseModel):
    name: str
    image: str


class HouseShareCreate(HouseShareBase):
    pass


class HouseShare(HouseShareBase):
    id: int
    creation_date: int
    users: List["UserBase"]

    class Config:
        from_attributes = True


class UserBase(BaseModel):
    first_name: str
    last_name: str
    email: str
    phone: str


class UserCreate(UserBase):
    password: str


class User(UserBase):
    id: int
    profile_picture: Optional[str]
    house_shares: List[HouseShareBase]

    class Config:
        from_attributes = True


class Token(BaseModel):
    access_token: str
    token_type: str


class TaskBase(BaseModel):
    name: str
    deadline: int
    done: bool
    assignee_id: int
    house_share_id: int


class TaskCreate(TaskBase):
    pass


class Task(TaskBase):
    id: int

    class Config:
        from_attributes = True


class DueBase(BaseModel):
    amount: float
    title: str
    creditor_id: int
    debtor_id: int
    house_share_id: int

    debtor_name: str
    creditor_name: str


class DueCreate(DueBase):
    pass


class Due(DueBase):
    id: int

    class Config:
        from_attributes = True


class EventBase(BaseModel):
    title: str
    date: int
    duration: int
    house_share_id: int


class EventCreate(EventBase):
    pass


class Event(EventBase):
    id: int

    class Config:
        from_attributes = True


class GroceryItemBase(BaseModel):
    name: str
    quantity: int
    bought: bool


class GroceryItemCreate(GroceryItemBase):
    list_id: int
    pass


class GroceryItem(GroceryItemBase):
    id: int


class GroceryListBase(BaseModel):
    name: str
    house_share_id: int
    assignee_id: int


class GroceryListCreate(GroceryListBase):
    pass


class GroceryList(GroceryListBase):
    id: int
    items: List[GroceryItem]

    class Config:
        from_attributes = True
