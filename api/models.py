from database import Base
from sqlalchemy import BigInteger, Boolean, Column, Enum, ForeignKey, Integer, String
from sqlalchemy.orm import relationship


class HouseShare(Base):
    __tablename__ = "house_share"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), nullable=False)
    creation_date = Column(Integer, nullable=False)
    image = Column(String(100))

    users = relationship(
        "User",
        secondary="house_share_member",
        back_populates="house_shares",
    )


class HouseShareMember(Base):
    __tablename__ = "house_share_member"
    house_share_id = Column(Integer, ForeignKey("house_share.id"), primary_key=True)
    user_id = Column(Integer, ForeignKey("user.id"), primary_key=True)


class Event(Base):
    __tablename__ = "event"

    id = Column(Integer, primary_key=True, index=True)
    title = Column(String(100), nullable=False)
    date = Column(Integer, nullable=False)
    duration = Column(Integer)
    house_share_id = Column(Integer, ForeignKey("house_share.id"), nullable=False)


class Due(Base):
    __tablename__ = "due"

    id = Column(Integer, primary_key=True, index=True)
    amount = Column(Integer, nullable=False)
    creditor_id = Column(Integer, ForeignKey("user.id"), nullable=False)
    debtor_id = Column(Integer, ForeignKey("user.id"), nullable=False)
    house_share_id = Column(Integer, ForeignKey("house_share.id"), nullable=False)

    debtor = relationship(
        "User", back_populates="debtor_dues", foreign_keys=[debtor_id]
    )
    creditor = relationship(
        "User", back_populates="creditor_dues", foreign_keys=[creditor_id]
    )


class Task(Base):
    __tablename__ = "task"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), nullable=False)
    deadline = Column(Integer)
    done = Column(Boolean, nullable=False, default=False)
    house_share_id = Column(Integer, ForeignKey("house_share.id"), nullable=False)
    assignee_id = Column(Integer, ForeignKey("user.id"))

    user = relationship("User", back_populates="tasks")


class GroceryList(Base):
    __tablename__ = "grocery_list"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), nullable=False)
    house_share_id = Column(Integer, ForeignKey("house_share.id"), nullable=False)
    assignee_id = Column(Integer, ForeignKey("user.id"))

    items = relationship("GroceryItem", back_populates="list")


class GroceryItem(Base):
    __tablename__ = "grocery_item"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), nullable=False)
    quantity = Column(String(100), nullable=False)
    bought = Column(Boolean, nullable=False, default=False)
    list_id = Column(Integer, ForeignKey("grocery_list.id"), nullable=False)


class User(Base):
    __tablename__ = "user"

    id = Column(Integer, primary_key=True, index=True)
    first_name = Column(String(100), nullable=False)
    last_name = Column(String(100), nullable=False)
    profile_picture = Column(String(100))
    email = Column(String(100), unique=True, nullable=False)
    phone = Column(String(14), nullable=False)
    password_hash = Column(String(100))

    creditor_dues = relationship(
        "Due", back_populates="creditor", foreign_keys=[Due.creditor_id]
    )
    debtor_dues = relationship(
        "Due", back_populates="debtor", foreign_keys=[Due.debtor_id]
    )
    house_shares = relationship(
        "HouseShare",
        secondary="house_share_member",
        back_populates="users",
    )
    tasks = relationship("Task", back_populates="user")
