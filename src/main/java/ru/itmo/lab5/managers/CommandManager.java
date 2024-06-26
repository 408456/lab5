package ru.itmo.lab5.managers;

import ru.itmo.lab5.comands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Управляет командами.
 *
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final List<String> commandHistory = new ArrayList<>();

    /**
     * Добавляет команду.
     *
     * @param commandName Название команды.
     * @param command     Команда.
     */
    public void commandAdd(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * @return Словарь команд.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * @return История команд.
     */
    public List<String> get8CommandHistory() {
        if (commandHistory.size() >= 8) return commandHistory.subList(commandHistory.size() - 8, commandHistory.size());
        else return commandHistory;
    }

    /**
     * Добавляет команду в историю.
     *
     * @param command Команда.
     */
    public void addToHistory(String command) {
        commandHistory.add(command);
    }

}