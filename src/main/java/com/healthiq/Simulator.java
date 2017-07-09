package com.healthiq;

import com.healthiq.processor.ActivityProcessor;
import org.apache.commons.cli.*;

import java.time.OffsetTime;
import java.util.Map;

/**
 * Main class for simulator
 */
public class Simulator
{
    private static final String shortFoodOption = "f";
    private static final String shortInputOption = "i";
    private static final String shortExerciseOption = "e";

    private static final Integer GLYCATION_LIMIT = 150;
    private static final String DELIMIITER = ",";

    private static final String START_OF_DAY_STRING =  "00:00Z";
    private static final String END_OF_DAY_STRING =  "23:59Z";

    private static Options getOptions() {
        final String longInputOption = "inputFile";
        final String longFoodOption = "foodDB";
        final String longExerciseOption = "exerciseDB";

        Options options = new Options();
        options.addOption( shortInputOption, longInputOption, true, "input file (CSV-formatted)" );
        options.addOption( shortFoodOption, longFoodOption, true, "foodDB input file" );
        options.addOption( shortExerciseOption, longExerciseOption, true, "exerciseDB input file" );
        return options;
    }

    public static void main(String[] args)
    {
        CommandLineParser parser = new DefaultParser();
        Options options = getOptions();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            if (!line.hasOption(shortFoodOption) || !line.hasOption(shortExerciseOption) ||
                    !line.hasOption(shortInputOption)) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "Simulator", options );
                return;
            }

            ExerciseMap.initialize(line.getOptionValue(shortExerciseOption));
            FoodMap.initialize(line.getOptionValue(shortFoodOption));
            Map<OffsetTime, Double> activity = ActivityProcessor.processInputFile(line.getOptionValue(shortInputOption));

            if (activity == null) {
                return;
            }

            Double bloodSugar = 80d;
            Integer glycation = 0;
            OffsetTime timestamp = OffsetTime.parse(START_OF_DAY_STRING);
            final OffsetTime lastMinuteOfDay = OffsetTime.parse(END_OF_DAY_STRING);
            System.out.println("Timestamp" + DELIMIITER + "Blood Sugar" + DELIMIITER + "Glycation");
            while (!timestamp.equals(lastMinuteOfDay)) {
                // print out entries every 15 minutes
                Double bloodSugarDelta = activity.get(timestamp);
                if (bloodSugarDelta != null) {
                    bloodSugar += bloodSugarDelta;
                    // if we have negative value, round to 0
                    if (bloodSugar < 0) {
                        bloodSugar = 0.0;
                    }
                }

                if (bloodSugar > GLYCATION_LIMIT) {
                    glycation++;
                }

                // print out blood sugar every 15 minutes
                if (timestamp.getMinute() % 15 == 0) {
                    System.out.println(timestamp + DELIMIITER + bloodSugar + DELIMIITER + glycation);
                }
                timestamp = timestamp.plusMinutes(1);
            }
        }
        catch( ParseException exp ) {
            System.out.println("Unexpected exception:" + exp.getMessage());
        }
    }
}
