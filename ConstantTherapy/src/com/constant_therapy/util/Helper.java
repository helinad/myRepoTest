package com.constant_therapy.util;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.dashboard.TaskTypeScores;
import com.constant_therapy.dashboard.TasksHierarchy;
import com.constant_therapy.dashboard.TasksType;

public class Helper {

	public static TasksType getSystemname(TaskTypeScores scores,
			String systemName) {
		if (scores.getActive_sentence_completion_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getActive_sentence_completion_tasks();
		} else if (scores.getAnalytical_reasoning().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getAnalytical_reasoning();
		} else if (scores.getArithmetic().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getArithmetic();
		} else if (scores.getAttention().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getAttention();
		} else if (scores.getAuditory().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getAuditory();
		} else if (scores.getAuditory_command_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getAuditory_command_tasks();
		} else if (scores.getCalendar_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getCalendar_tasks();
		} else if (scores.getClock_math_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getClock_math_tasks();
		} else if (scores.getClock_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getClock_tasks();
		} else if (scores.getCognitive().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getCognitive();
		} else if (scores.getCurrency_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getCurrency_tasks();
		} else if (scores.getEnvironmental_sound_memory_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getEnvironmental_sound_memory_tasks();
		} else if (scores.getExecutive_skills().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getExecutive_skills();
		} else if (scores.getFace_memory_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getFace_memory_tasks();
		} else if (scores.getFeature_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getFeature_tasks();
		} else if (scores.getFlanker_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getFlanker_tasks();
		} else if (scores.getFunctional_math_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getFunctional_math_tasks();
		} else if (scores.getLanguage().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getLanguage();
		} else if (scores.getLanguage_auditory().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getLanguage_auditory();
		} else if (scores.getLetter_to_phoneme_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getLetter_to_phoneme_tasks();
		} else if (scores.getLong_reading_passage_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getLong_reading_passage_tasks();
		} else if (scores.getMap_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getMap_tasks();
		} else if (scores.getMath_addition_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getMath_addition_tasks();
		} else if (scores.getMath_division_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getMath_division_tasks();
		} else if (scores.getMath_multiplication_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getMath_multiplication_tasks();
		} else if (scores.getMath_subtraction_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getMath_subtraction_tasks();
		} else if (scores.getMath_word_problem_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getMath_word_problem_tasks();
		} else if (scores.getMemory().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getMemory();
		} else if (scores.getMental_flexibility().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getMental_flexibility();
		} else if (scores.getMental_rotation_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getMental_rotation_tasks();
		} else if (scores.getNaming().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getNaming();
		} else if (scores.getNaming_picture_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getNaming_picture_tasks();
		} else if (scores.getNumber_pattern_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getNumber_pattern_tasks();
		} else if (scores.getPassive_sentence_completion_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getPassive_sentence_completion_tasks();
		} else if (scores.getPattern_recreation_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getPattern_recreation_tasks();
		} else if (scores.getPhoneme_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getPhoneme_tasks();
		} else if (scores.getPhoneme_to_letter_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getPhoneme_to_letter_tasks();
		} else if (scores.getPicture_memory_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getPicture_memory_tasks();
		} else if (scores.getPicture_nback_memory_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getPicture_nback_memory_tasks();
		} else if (scores.getPicture_ordering_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getPicture_ordering_tasks();
		} else if (scores.getPicture_spelling_completion_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getPicture_spelling_completion_tasks();
		} else if (scores.getPicture_spelling_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getPicture_spelling_tasks();
		} else if (scores.getPlanning().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getPlanning();
		} else if (scores.getPlaying_card_slapjack_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getPlaying_card_slapjack_tasks();
		} else if (scores.getProblem_solving().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getProblem_solving();
		} else if (scores.getQuantitative_reasoning().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getQuantitative_reasoning();
		} else if (scores.getReading().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getReading();
		} else if (scores.getReading_passage_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getReading_passage_tasks();
		} else if (scores.getResponse_inhibition().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getResponse_inhibition();
		} else if (scores.getRhyming_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getRhyming_tasks();
		} else if (scores.getScanning().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getScanning();
		} else if (scores.getSentence_completion().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getSentence_completion();
		} else if (scores.getSentence_planning().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getSentence_planning();
		} else if (scores.getSequencing_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getSequencing_tasks();
		} else if (scores.getSimple_word_production_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getSimple_word_production_tasks();
		} else if (scores.getSorting_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getSorting_tasks();
		} else if (scores.getSound_memory_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getSound_memory_tasks();
		} else if (scores.getSpoken_phoneme_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getSpoken_phoneme_tasks();
		} else if (scores.getSpoken_rhyming_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getSpoken_rhyming_tasks();
		} else if (scores.getSpoken_syllable_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getSpoken_syllable_tasks();
		} else if (scores.getSpoken_word_comprehension_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getSpoken_word_comprehension_tasks();
		} else if (scores.getSymbol_matching_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getSymbol_matching_tasks();
		} else if (scores.getVisual_processing().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getVisual_processing();
		} else if (scores.getVisuospatial().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getVisuospatial();
		} else if (scores.getVoicemail_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getVoicemail_tasks();
		} else if (scores.getWord_coordinate_judgment_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getWord_coordinate_judgment_tasks();
		} else if (scores.getWord_copy_completion_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getWord_copy_completion_tasks();
		} else if (scores.getWord_copy_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getWord_copy_tasks();
		} else if (scores.getWord_identification_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getWord_identification_tasks();
		} else if (scores.getWord_memory_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getWord_copy_tasks();
		} else if (scores.getWord_ordering_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getWord_ordering_tasks();
		} else if (scores.getWord_spelling_completion_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getWord_spelling_completion_tasks();
		} else if (scores.getWord_spelling_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getWord_spelling_tasks();
		} else if (scores.getWriting().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getWriting();
		} else if (scores.getWritten_lexical_decision_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getWritten_lexical_decision_tasks();
		} else if (scores.getWritten_word_comprehension_tasks().getTaxonomy()
				.equalsIgnoreCase(systemName)) {
			return scores.getWritten_word_comprehension_tasks();
		}

		return null;
	}

	public static String getDateFromTimeStamp(long time) {

		Date df = new java.util.Date(time);
		SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");
		String str = null;
		str = sf.format(df);

		return str;
	}

	public static Calendar getStringToDate(String calendar) {

		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final Calendar c = Calendar.getInstance();
		try {
			c.setTime(df.parse(calendar));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return c;

	}

	public static int colorForScoreAndMean(double score, double mean) {
		int max = 233;
		int R = 0, G = 0, B = 0;
		if (score <= mean) {
			R = max;
			G = (int) Math.floor(max * score / mean);
		} else {
			G = max;
			R = (int) Math.floor(max * (1 - score) / (1 - mean));
		}
		return Color.rgb(R, G, B); // creates colors with RGB [0,255]
	}

	public static Boolean isRight(Activity context) {
		SharedPreferences prefs = context.getSharedPreferences("flipinfo",
				Context.MODE_PRIVATE);
		boolean isRight = prefs.getBoolean("flip", false);
		return isRight;
	}

	public static void setIsRight(Activity context, Boolean isRight) {
		Editor e = context.getSharedPreferences("flipinfo",
				Context.MODE_PRIVATE).edit();
		e.putBoolean("flip", isRight);
		e.commit();
	}

	public static int getContainter(String displayName, Boolean isRight) {
		if (Helper.isMatching(displayName)
				|| Helper.isSymbolMatching(displayName)) {
			return R.layout.grid_container;
		} else if (Helper.isPatternTask(displayName)) {
			return R.layout.pattern_container;
		} else if (Helper.isCurrency(displayName)) {
			return R.layout.currency_container;
		} else if (Helper.isDragandDrop(displayName)) {
			return R.layout.draganddrop_container;
		} else if (Helper.isAuditory(displayName)) {
			return R.layout.auditory_container;
		} else if (Helper.isArithmetic(displayName)) {
			if (isRight)
				return R.layout.arithmetic_container;
			else
				return R.layout.arithmetic_container_left;
		} else if (Helper.isLetterToSound(displayName)
				|| Helper.isSoundToLetter(displayName)
				|| Helper.isSpokenWordTask(displayName)
				|| Helper.isActiveTask(displayName)
				|| Helper.isOrderingTask(displayName)) {
			return R.layout.matching_dragdrop_container;
		} else if (Helper.isWrittenTask(displayName)) {
			return R.layout.written_task_container;
		} else if (Helper.isWordProblem(displayName)) {
			return R.layout.word_problem_container;
		} else if (Helper.isPlayingCards(displayName)) {
			return R.layout.playing_container;
		}else if (Helper.isMicrophoneTask(displayName)) {
			if (isRight)
				return R.layout.microphone_innercontainer;
			else
				return R.layout.microphone_innercontainer_left;
		} else {

			if (displayName.equalsIgnoreCase("3")
					|| displayName.equalsIgnoreCase("2")
					|| displayName.equalsIgnoreCase("4")
					|| displayName.equalsIgnoreCase("5")
					|| displayName.equalsIgnoreCase("1")) {
				if (isRight)
					return R.layout.multiple_type_innercontainer1;
				else
					return R.layout.multiple_type_innercontainer_left1;
			} if (displayName.equalsIgnoreCase("32")
					|| displayName.equalsIgnoreCase("55") || displayName.equalsIgnoreCase("54")) {
				
					return R.layout.multiple_type_innercontainer3;
			} else {
				if (isRight)
					return R.layout.multiple_type_innercontainer2;
				else
					return R.layout.multiple_type_innercontainer_left2;
			}

		}
	}

	public static String getUrl(Activity context, String displayName) {
		if (displayName.equalsIgnoreCase("Voice Mail")
				|| displayName.equalsIgnoreCase("Word Identification"))
			return context.getString(R.string.remote_audiochoice_tasks);
		else if (displayName.equalsIgnoreCase("Spoken Sound")
				|| displayName.equalsIgnoreCase("Spoken Rhyming")
				|| displayName.equalsIgnoreCase("Spoken Syllable"))
			return context.getString(R.string.remote_audioyesorno_tasks);
		else if (displayName.equalsIgnoreCase("Category Matching")
				|| displayName.equalsIgnoreCase("Map Reading"))
			return context.getString(R.string.remote_multichoice_tasks);
		else if (displayName.equalsIgnoreCase("Syllable Identification")
				|| displayName.equalsIgnoreCase("Rhyming")
				|| displayName.equalsIgnoreCase("Sound Identification")
				|| displayName.equalsIgnoreCase("Feature Matching"))
			return context.getString(R.string.remote_yesorno_tasks);
		else if (displayName.equalsIgnoreCase("Clock Reading")
				|| displayName.equalsIgnoreCase("Clock Math"))
			return context.getString(R.string.remote_clockmath_tasks);
		else if (displayName.equalsIgnoreCase("Flanker") || displayName.equalsIgnoreCase("Written Lexical Decision") || displayName.equalsIgnoreCase("Mental Rotation"))
			return context.getString(R.string.remote_flanker_task);

		return null;
	}

	
	public static Boolean isMultiTask(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 40 || typeId == 38 || typeId == 39 || typeId == 33
				|| typeId == 3 || typeId == 1 || typeId == 2 || typeId == 4
				|| typeId == 5 || typeId == 16 || typeId == 17 || typeId == 18
				|| typeId == 36 || typeId == 54 || typeId == 23 || typeId == 22
				|| typeId == 49 || typeId == 55 || typeId == 32 || typeId == 33
				|| typeId == 22 || typeId == 31) {
			return true;
		} else
			return false;
	}

	public static Boolean isMicrophoneTask(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 57 || typeId == 58 || typeId == 51 || typeId == 19
				|| typeId == 59 || typeId == 60) {
			return true;
		} else
			return false;
	}

	public static TasksHierarchy getLevelItems(String displayName,
			List<TasksHierarchy> tasksHierarchy) {
		// TODO Auto-generated method stub
		for (int i = 0; i < tasksHierarchy.size(); i++) {
			if (displayName.equalsIgnoreCase(tasksHierarchy.get(i)
					.getDisplayName())) {
				return tasksHierarchy.get(i);
			}
		}
		return null;
	}

	public static int getMaxLevel(String displayName,
			List<TasksHierarchy> tasksHierarchy) {
		// TODO Auto-generated method stub
		for (int i = 0; i < tasksHierarchy.size(); i++) {
			if (displayName.equalsIgnoreCase(tasksHierarchy.get(i)
					.getDisplayName())) {
				return tasksHierarchy.get(i).getMaxLevel();
			}
		}
		return 0;
	}

	public static Boolean isMatching(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 24 || typeId == 25 || typeId == 53 || typeId == 26
				|| typeId == 48) {
			return true;
		} else
			return false;
	}

	public static Boolean isSoundToLetter(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 10) {
			return true;
		} else
			return false;
	}

	public static Boolean isPatternTask(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 56) {
			return true;
		} else
			return false;
	}

	public static Boolean isLetterToSound(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 9) {
			return true;
		} else
			return false;
	}
	
	public static Boolean isCurrency(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 43) {
			return true;
		} else
			return false;
	}

	public static Boolean isWrittenTask(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 28 || typeId == 29 || typeId == 30 || typeId == 6
				|| typeId == 8 || typeId == 7) {
			return true;
		} else
			return false;
	}

	public static Boolean isArithmetic(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 11 || typeId == 12 || typeId == 13 || typeId == 14) {
			return true;
		} else
			return false;
	}

	public static Boolean isWordProblem(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 15 || typeId == 45) {
			return true;
		} else
			return false;
	}

	public static Boolean isOrderingTask(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 20 || typeId == 21) {
			return true;
		} else
			return false;
	}

	public static Boolean isSpokenWordTask(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 42 || typeId == 41) {
			return true;
		} else
			return false;
	}

	public static Boolean isDragandDrop(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 37) {
			return true;
		} else
			return false;
	}

	public static Boolean isPlayingCards(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 46 || typeId == 47) {
			return true;
		} else
			return false;
	}

	public static Boolean isActiveTask(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 35 || typeId == 34) {
			return true;
		} else
			return false;
	}

	public static Boolean isSymbolMatching(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 27) {
			return true;
		} else
			return false;
	}

	public static Boolean isAuditory(String taskTypeId) {
		int typeId = Integer.parseInt(taskTypeId);
		if (typeId == 44) {
			return true;
		} else
			return false;
	}

	public static long getCurrentTimeStamp() {
		try {
			Calendar calendar = Calendar.getInstance();

			long currentTimeStamp = calendar.getTimeInMillis() / 1000;// Find
																		// todays
																		// date

			return currentTimeStamp;
		} catch (Exception e) {
			e.printStackTrace();

			return 0;
		}
	}
	
	public static void trimCache(Context context) {
	      try {
	         File dir = context.getCacheDir();
	         if (dir != null && dir.isDirectory()) {
	            deleteDir(dir);
	         }
	      } catch (Exception e) {
	         // TODO: handle exception
	      }
	   }

	   public static boolean deleteDir(File dir) {
	      if (dir != null && dir.isDirectory()) {
	         String[] children = dir.list();
	         for (int i = 0; i < children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	               return false;
	            }
	         }
	      }

	      // The directory is now empty so delete it
	      return dir.delete();
	   }


}
