package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<String>> mondaySession = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySession.size());
        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<String>> tuesdaySession = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertNull(tuesdaySession);

    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<String>> mondaySession = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySession.size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<String>> thursdaySession = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySession.size());

        ArrayList<TimeOfDay> sortedTimes = new ArrayList<>(thursdaySession.keySet());
        Assertions.assertEquals(new TimeOfDay(13, 0), sortedTimes.get(0));
        Assertions.assertEquals(new TimeOfDay(20, 0), sortedTimes.get(1));
        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<String>> tuesdaySession = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertNull(tuesdaySession);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<String> monday1300 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, singleTrainingSession.getTimeOfDay());
        Assertions.assertEquals(1,
                monday1300.size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<String> monday1400 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertNull(monday1400);
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Скворцов", "Владимир", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession1 = new TrainingSession(group, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(15, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));


        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);

        List<CounterOfTrainings> result = timetable.getCountByCoaches();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.get(0).toString().contains("Васильев Николай Сергеевич"));
    }

}
