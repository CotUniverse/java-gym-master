package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private final int counter;
    Coach coach;

    public CounterOfTrainings(Coach coach, int counter) {
        this.coach = coach;
        this.counter = counter;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return Integer.compare(o.counter, this.counter);
    }

    public String toString() {
        return "Тренер " + coach + ", количество тренировок " + counter;
    }
}
