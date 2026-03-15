package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<String>>> timetable = new LinkedHashMap<>();
    private final HashMap<String, Integer> countTrainingsTable = new HashMap<>();
    ArrayList<CounterOfTrainings> counterList = new ArrayList<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, ArrayList<String>> daySchedule = timetable.computeIfAbsent(day, k -> new TreeMap<>());

        ArrayList<String> training = daySchedule.computeIfAbsent(time, k -> new ArrayList<>());
        training.add(trainingSession.toString());
        //сохраняем занятие в расписании
    }

    public TreeMap<TimeOfDay, ArrayList<String>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return !timetable.isEmpty() ? timetable.get(dayOfWeek) : null;
    }

    public ArrayList<String> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return !timetable.get(dayOfWeek).isEmpty() ? timetable.get(dayOfWeek).get(timeOfDay) : null;
    }

    public ArrayList<CounterOfTrainings> getCountByCoaches() {
        for (Map.Entry<String, Integer> entry : countTrainingsForCoaches().entrySet()) {
            counterList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        counterList.sort(null);

        return counterList;
    }

    public HashMap<String, Integer> countTrainingsForCoaches() {
        for (TreeMap<TimeOfDay, ArrayList<String>> daySchedule : timetable.values()) {
            for (ArrayList<String> sessions : daySchedule.values()) {
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
