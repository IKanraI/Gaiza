package util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import java.util.List;

public class GaizaUtil {

    public static String getPassedArgument(List<SlashCommandInteractionOption> args) {
        String term = "";
        if (CollectionUtils.isNotEmpty(args) && args.get(0).getStringValue().isPresent())
            term = args.get(0).getStringValue().get();

        return term;
    }
}
