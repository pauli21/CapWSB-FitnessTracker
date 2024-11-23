package com.capgemini.wsb.fitnesstracker.statistics.api;

import java.util.List;
/**
 * Interfejs serwisu odpowiedzialnego za zarządzanie statystykami użytkowników.
 * Zawiera metody do tworzenia, aktualizowania, pobierania, usuwania oraz generowania raportów.
 */
public interface StatisticService {

    /**
     * Tworzy nową statystykę.
     *
     * @param statistics Obiekt statystyki do utworzenia.
     * @return Utworzona statystyka.
     */
    Statistics createStatistics(Statistics statistics);

    /**
     * Aktualizuje istniejącą statystykę.
     *
     * @param id Identyfikator statystyki do zaktualizowania.
     * @param updatedStatistics Nowe dane statystyki.
     * @return Zaktualizowana statystyka.
     */
    Statistics updateStatistics(Long id, Statistics updatedStatistics);

    /**
     * Pobiera statystyki dla użytkownika o podanym identyfikatorze.
     *
     * @param userId Identyfikator użytkownika.
     * @return Lista statystyk przypisanych do użytkownika.
     */
    List<Statistics> getStatisticsByUserId(Long userId);

    /**
     * Usuwa statystykę o podanym identyfikatorze.
     *
     * @param id Identyfikator statystyki do usunięcia.
     */
    void deleteStatistics(Long id);

    /**
     * Pobiera statystyki, w których liczba spalonych kalorii jest większa niż podana wartość.
     *
     * @param calories Minimalna liczba kalorii.
     * @return Lista statystyk, w których liczba spalonych kalorii jest większa niż podana wartość.
     */
    List<Statistics> getStatisticsByCaloriesGreaterThan(int calories);

    /**
     * Pobiera wszystkie raporty.
     *
     * @return Lista wszystkich raportów.
     */
    List<Statistics> getAllReports();

    /**
     * Pobiera treść raportu dla użytkownika, miesiąca i roku.
     *
     * @param userId Identyfikator użytkownika.
     * @param month Miesiąc raportu.
     * @param year Rok raportu.
     * @return Treść raportu w postaci tekstu.
     */
    String getReportContent(Long userId, int month, int year);

    /**
     * Generuje miesięczny raport i wysyła go na e-mail użytkownika.
     *
     * @param userId Identyfikator użytkownika.
     * @param month Miesiąc raportu.
     * @param year Rok raportu.
     */
    void generateAndSendMonthlyReport(Long userId, int month, int year);
}
