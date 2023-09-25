from sqlalchemy.orm import Session
from fastapi import HTTPException

from ..dto import schemas
from ..database import models
from ..config.logger import logger


def create_user(participant: schemas.ParticipantCreate, db: Session):
    logger.info("Creating participant")

    db_participant = models.Participant(
        document = participant.document,
        agency = participant.agency,
        account = participant.account,
        balance = 0
    )

    db.add(db_participant)
    db.commit()
    return db_participant

def update_balance(participantId: int, differece: int, db: Session):
    logger.info(f"Updating participant`s {participantId} balance")
    db_participant = db.query(models.Participant).filter(models.Participant.id == participantId).first()
    
    if (differece < 0 and db_participant.balance + differece < 0):
        logger.error(f"Participant: {participantId} does not have sufficient balance. Current balance: {db_participant.balance} difference: {differece}")
        raise HTTPException(status_code=400, detail="Participant does not have sufficient balance")
    
    db_participant.balance = db_participant.balance + differece
    db.commit()
    return db_participant