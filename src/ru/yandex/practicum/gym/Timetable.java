package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<String>>> timetable = new LinkedHashMap<>();
    private final Map<String, Integer> countTrainingsTable = new HashMap<>();
    List<CounterOfTrainings> counterList = new ArrayList<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        Map<TimeOfDay, List<String>> daySchedule = timetable.computeIfAbsent(day, k -> new TreeMap<>());

        List<String> training = daySchedule.computeIfAbsent(time, k -> new ArrayList<>());
        training.add(trainingSession.toString());
        //сохраняем занятие в расписании
    }

    public Map<TimeOfDay, List<String>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<String> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return !timetable.get(dayOfWeek).isEmpty() ? timetable.get(dayOfWeek).get(timeOfDay) : Collections.emptyList();
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        for (Map.Entry<String, Integer> entry : countTrainingsForCoaches().entrySet()) {
            counterList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        counterList.sort(null);

        return counterList;
    }

    public Map<String, Integer> countTrainingsForCoaches() {
        for (TreeMap<TimeOfDay, List<String>> daySchedule : timetable.values()) {
            for (List<String> sessions : daySchedule.values()) {
                for (String session : sessions) {
                    String[] parts = session.split("Ф.И.О");
                    for (int i = 1; i < parts.length; i++) {
                        String coach = parts[i].trim();
                        countTrainingsTable.put(coach, countTrainingsTable.getOrDefault(coach, 0) + 1);
                    }
                }
            }
        }

        return countTrainingsTable;
    }
}
