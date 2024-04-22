from typing import List, Optional

from pydantic import BaseModel


class HouseShareBase(BaseModel):
    name: str
    image: str


class HouseShareCreate(HouseShareBase):
    pass


class HouseShare(HouseShareBase):
    id: int
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
    profile_picture: str
    house_shares: List[HouseShareBase]
    tasks: List["TaskBase"]

    class Config:
        from_attributes = True


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
    creditor_id: int
    debtor_id: int
    house_share_id: int


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
    list_id: int


class GroceryItemCreate(GroceryItemBase):
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
