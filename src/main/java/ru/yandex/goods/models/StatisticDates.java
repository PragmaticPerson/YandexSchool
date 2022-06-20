package ru.yandex.goods.models;

import java.time.LocalDateTime;

public class StatisticDates {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public StatisticDates(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean isNull() {
        return start == null && end == null;
    }

    public boolean isOneNull() {
        return start == null || end == null;
    }

    public boolean isCorrect() {
        return !isNull() && start.isBefore(end);
    }
}
