package com.cortez.adventuremod.commands;

import com.cortez.adventuremod.calendar.CalendarDate;
import com.cortez.adventuremod.calendar.CalendarManager;
import com.cortez.adventuremod.calendar.Season;
import com.cortez.adventuremod.calendar.SeasonEffects;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Comandos relacionados ao sistema de calendário.
 */
public class CalendarCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("calendar")
                .then(CommandManager.literal("date")
                    .executes(CalendarCommands::showDate))
                .then(CommandManager.literal("time")
                    .executes(CalendarCommands::showTime))
                .then(CommandManager.literal("season")
                    .executes(CalendarCommands::showSeason))
                .then(CommandManager.literal("info")
                    .executes(CalendarCommands::showFullInfo))
                .then(CommandManager.literal("set")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.literal("day")
                        .then(CommandManager.argument("day", IntegerArgumentType.integer(1, 112))
                            .executes(CalendarCommands::setDay)))
                    .then(CommandManager.literal("year")
                        .then(CommandManager.argument("year", IntegerArgumentType.integer(1))
                            .executes(CalendarCommands::setYear)))
                    .then(CommandManager.literal("season")
                        .then(CommandManager.literal("spring")
                            .executes(ctx -> setSeason(ctx, Season.SPRING)))
                        .then(CommandManager.literal("summer")
                            .executes(ctx -> setSeason(ctx, Season.SUMMER)))
                        .then(CommandManager.literal("autumn")
                            .executes(ctx -> setSeason(ctx, Season.AUTUMN)))
                        .then(CommandManager.literal("winter")
                            .executes(ctx -> setSeason(ctx, Season.WINTER)))))
        );

        // Alias /date
        dispatcher.register(
            CommandManager.literal("date")
                .executes(CalendarCommands::showDate)
        );

        // Alias /season
        dispatcher.register(
            CommandManager.literal("season")
                .executes(CalendarCommands::showSeason)
        );
    }

    private static int showDate(CommandContext<ServerCommandSource> context) {
        CalendarManager manager = CalendarManager.get(context.getSource().getServer());
        CalendarDate date = manager.getCurrentDate();

        context.getSource().sendFeedback(
            () -> Text.literal("📅 ").formatted(Formatting.GOLD)
                .append(Text.literal(date.getFormattedDate()).formatted(Formatting.YELLOW)),
            false
        );
        return 1;
    }

    private static int showTime(CommandContext<ServerCommandSource> context) {
        CalendarManager manager = CalendarManager.get(context.getSource().getServer());
        CalendarDate date = manager.getCurrentDate();

        String timeEmoji = date.isDaytime() ? "☀" : "🌙";
        Formatting timeColor = date.isDaytime() ? Formatting.YELLOW : Formatting.BLUE;

        context.getSource().sendFeedback(
            () -> Text.literal(timeEmoji + " ").formatted(timeColor)
                .append(Text.literal(date.getFormattedTime()).formatted(Formatting.WHITE)),
            false
        );
        return 1;
    }

    private static int showSeason(CommandContext<ServerCommandSource> context) {
        CalendarManager manager = CalendarManager.get(context.getSource().getServer());
        Season season = manager.getCurrentSeason();

        Formatting seasonColor = getSeasonColor(season);
        String seasonEmoji = getSeasonEmoji(season);

        context.getSource().sendFeedback(
            () -> Text.literal(seasonEmoji + " Estação: ").formatted(Formatting.GRAY)
                .append(Text.literal(season.getDisplayName()).formatted(seasonColor)),
            false
        );
        return 1;
    }

    private static int showFullInfo(CommandContext<ServerCommandSource> context) {
        CalendarManager manager = CalendarManager.get(context.getSource().getServer());
        CalendarDate date = manager.getCurrentDate();
        Season season = manager.getCurrentSeason();

        Formatting seasonColor = getSeasonColor(season);
        String seasonEmoji = getSeasonEmoji(season);

        context.getSource().sendFeedback(
            () -> Text.literal("═══════ Calendário ═══════").formatted(Formatting.GOLD),
            false
        );

        context.getSource().sendFeedback(
            () -> Text.literal("📅 Data: ").formatted(Formatting.GRAY)
                .append(Text.literal(date.getFormattedDate()).formatted(Formatting.WHITE)),
            false
        );

        context.getSource().sendFeedback(
            () -> Text.literal("⏰ Hora: ").formatted(Formatting.GRAY)
                .append(Text.literal(date.getFormattedTime()).formatted(Formatting.WHITE)),
            false
        );

        context.getSource().sendFeedback(
            () -> Text.literal(seasonEmoji + " Estação: ").formatted(Formatting.GRAY)
                .append(Text.literal(season.getDisplayName()).formatted(seasonColor)),
            false
        );

        context.getSource().sendFeedback(
            () -> Text.literal("📆 Ano: ").formatted(Formatting.GRAY)
                .append(Text.literal(String.valueOf(date.getYear())).formatted(Formatting.WHITE)),
            false
        );

        context.getSource().sendFeedback(
            () -> Text.literal("🌡 Mod. Temperatura: ").formatted(Formatting.GRAY)
                .append(Text.literal(String.format("%.1f", SeasonEffects.getTemperatureModifier(season)))
                    .formatted(Formatting.AQUA)),
            false
        );

        context.getSource().sendFeedback(
            () -> Text.literal("🌱 Mod. Crescimento: ").formatted(Formatting.GRAY)
                .append(Text.literal(String.format("%.1fx", SeasonEffects.getCropGrowthModifier(season)))
                    .formatted(Formatting.GREEN)),
            false
        );

        context.getSource().sendFeedback(
            () -> Text.literal("═══════════════════════════").formatted(Formatting.GOLD),
            false
        );

        return 1;
    }

    private static int setDay(CommandContext<ServerCommandSource> context) {
        int day = IntegerArgumentType.getInteger(context, "day");
        CalendarManager manager = CalendarManager.get(context.getSource().getServer());
        CalendarDate date = manager.getCurrentDate();

        // Calcula o novo tempo do mundo
        int year = date.getYear();
        long newTime = ((long)(year - 1) * CalendarDate.DAYS_PER_YEAR + (day - 1)) * CalendarDate.TICKS_PER_DAY;

        context.getSource().getServer().getOverworld().setTimeOfDay(newTime);

        context.getSource().sendFeedback(
            () -> Text.literal("Dia alterado para " + day).formatted(Formatting.GREEN),
            true
        );
        return 1;
    }

    private static int setYear(CommandContext<ServerCommandSource> context) {
        int year = IntegerArgumentType.getInteger(context, "year");
        CalendarManager manager = CalendarManager.get(context.getSource().getServer());
        CalendarDate date = manager.getCurrentDate();

        // Mantém o dia atual mas muda o ano
        int dayOfYear = date.getDayOfYear();
        long newTime = ((long)(year - 1) * CalendarDate.DAYS_PER_YEAR + (dayOfYear - 1)) * CalendarDate.TICKS_PER_DAY;

        context.getSource().getServer().getOverworld().setTimeOfDay(newTime);

        context.getSource().sendFeedback(
            () -> Text.literal("Ano alterado para " + year).formatted(Formatting.GREEN),
            true
        );
        return 1;
    }

    private static int setSeason(CommandContext<ServerCommandSource> context, Season season) {
        CalendarManager manager = CalendarManager.get(context.getSource().getServer());
        CalendarDate date = manager.getCurrentDate();

        // Calcula o primeiro dia da estação
        int firstDayOfSeason = (season.getIndex() * CalendarDate.DAYS_PER_SEASON) + 1;
        int year = date.getYear();
        long newTime = ((long)(year - 1) * CalendarDate.DAYS_PER_YEAR + (firstDayOfSeason - 1)) * CalendarDate.TICKS_PER_DAY;

        context.getSource().getServer().getOverworld().setTimeOfDay(newTime);

        context.getSource().sendFeedback(
            () -> Text.literal("Estação alterada para " + season.getDisplayName()).formatted(Formatting.GREEN),
            true
        );
        return 1;
    }

    private static Formatting getSeasonColor(Season season) {
        return switch (season) {
            case SPRING -> Formatting.GREEN;
            case SUMMER -> Formatting.YELLOW;
            case AUTUMN -> Formatting.GOLD;
            case WINTER -> Formatting.AQUA;
        };
    }

    private static String getSeasonEmoji(Season season) {
        return switch (season) {
            case SPRING -> "🌸";
            case SUMMER -> "☀";
            case AUTUMN -> "🍂";
            case WINTER -> "❄";
        };
    }
}

