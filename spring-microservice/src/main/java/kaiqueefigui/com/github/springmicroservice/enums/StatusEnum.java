package kaiqueefigui.com.github.springmicroservice.enums;

import kaiqueefigui.com.github.springmicroservice.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    NOT_PAID(1L),
    PAID(2L),
    PAYMENT_IN_PROGRESS(3L),
    PAYMENT_ERROR(4L);

    private Long id;

    public Status getEntity() {
        return new Status(this.getId(), null);
    }
}
