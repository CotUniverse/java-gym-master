package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new LinkedHashMap<>();
    private final Map<Coach, Integer> countTrainingsTable = new HashMap<>();
    List<CounterOfTrainings> counterList = new ArrayList<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        Map<TimeOfDay, List<TrainingSession>> daySchedule = timetable.computeIfAbsent(day, k -> new TreeMap<>());

        List<TrainingSession> training = daySchedule.computeIfAbsent(time, k -> new ArrayList<>());
        training.add(trainingSession);
        //сохраняем занятие в расписании
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return !timetable.get(dayOfWeek).isEmpty() ? timetable.get(dayOfWeek).get(timeOfDay) : Collections.emptyList();
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        for (Map.Entry<Coach, Integer> entry : countTrainingsForCoaches().entrySet()) {
            counterList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        counterList.sort(null);

        return counterList;
    }

    public Map<Coach, Integer> countTrainingsForCoaches() {
        countTrainingsTable.clear();

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySchedule : timetable.values()) {
            for (List<TrainingSession> sessions : daySchedule.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    countTrainingsTable.put(coach, countTrainingsTable.getOrDefault(coach, 0) + 1);
                }
            }
        }

        return countTrainingsTable;
    }
}
