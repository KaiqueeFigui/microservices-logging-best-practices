from sqlalchemy.orm import Session

from ..dto import schemas
from ..database import models


def create_user(participant: schemas.ParticipantCreate, db: Session):
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
    db_participant = db.query(models.Participant).filter(models.Participant.id == participantId).first()
    
    if (differece < 0 and db_participant.balance + differece < 0):
        raise BaseException("Participant does not have sufficient balance")
    
    db_participant.balance = db_participant.balance + differece
    db.commit()
    return db_participant