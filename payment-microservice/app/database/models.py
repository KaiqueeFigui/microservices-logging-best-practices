from sqlalchemy import Boolean, Column, ForeignKey, Integer, String, Float, DateTime, ForeignKey
from sqlalchemy.orm import relationship

from .database import Base

class Participant(Base):
    __tablename__ = "participant"
    id = Column(Integer, primary_key=True, index=True)
    document = Column(String, unique=True, index=True)
    account = Column(String, index=True)
    agency = Column(String, index=True)
    balance = Column(Float, index=True)

class Transaction(Base):
    __tablename__ = "transaction"
    id = Column(Integer, primary_key=True, index=True)
    value = Column(Float, index=True)
    description = Column(String, index=True)
    date_time = Column(DateTime, index=True)

    payer_id = Column("payer_id", Integer, ForeignKey('participant.id'))
    receiver_id = Column("receiver_id", Integer, ForeignKey('participant.id'))