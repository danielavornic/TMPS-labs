package moviematcher.config;

import moviematcher.model.Mood;
import moviematcher.model.Situation;
import java.util.List;
import java.util.Arrays;

public class MovieMatcherConfig {
  public static final List<Mood> VALID_MOODS = Arrays.asList(Mood.values());
  public static final List<Situation> VALID_SITUATIONS = Arrays.asList(Situation.values());
  public static final String DEFAULT_MOVIE = "Forrest Gump";

  public static final String TRY_AGAIN_OPTION = "Try again (same mood and situation)";
  public static final String CHOOSE_DIFFERENT_OPTION = "Choose a different mood and situation";
  public static final String QUIT_OPTION = "Quit";

  public static final List<String> POST_RECOMMENDATION_OPTIONS = Arrays.asList(
      TRY_AGAIN_OPTION,
      CHOOSE_DIFFERENT_OPTION,
      QUIT_OPTION);
}
