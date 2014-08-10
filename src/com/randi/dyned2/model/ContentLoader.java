package com.randi.dyned2.model;

import net.rim.device.api.ui.UiApplication;

import com.awan.dyned2.general.GeneralFunction;
import com.randi.dyned2.tools.ProgressListener;
import com.randi.dyned2.tools.ThreadManager;

/**
 * A thread class to download Audio and Avatar video files.
 * @author Randi Waranugraha
 *
 */
public class ContentLoader extends Thread implements ContentListener {
	
	private ProgressListener listener;
	private ThreadManager threadManager = ThreadManager.getInstance();
	private AudioDownload audioDownload = AudioDownload.getInstance();
	private AvatarDownload avatarDownload = AvatarDownload.getInstance();
	
	private int totalProgress;
	private int currentProgress;
	
	private static final String[] DIALOGUES_LESSON_ONE = {
		"NDE0201A.mp3", "NDE0201B.mp3", "NDE0201C.mp3"
	};
	
	private static final String[] QUESTIONS_LESSON_ONE = {
		"NDE0201B_Q1.mp3", "NDE0201B_Q2.mp3", "NDE0201B_Q3.mp3"
	};
	
	private static final String[] DIALOGUES = {
		"NDE0202A.mp3", "NDE0202B.mp3", "NDE0202C.mp3",

    	"NDE0203A.mp3", "NDE0203B.mp3", "NDE0203C.mp3", "NDE0203_BONUS.mp3",

    	"NDE0204A.mp3", "NDE0204B.mp3", "NDE0204C.mp3",

    	"NDE0205A.mp3", "NDE0205B.mp3", "NDE0205C.mp3",

    	"NDE0206A.mp3", "NDE0206B.mp3", "NDE0206C.mp3", "NDE0206_BONUS.mp3",

    	"NDE0207A.mp3", "NDE0207B.mp3", "NDE0207C.mp3",

    	"NDE0208A.mp3", "NDE0208B.mp3", "NDE0208C.mp3",

    	"NDE0209A.mp3", "NDE0209B.mp3", "NDE0209C.mp3", "NDE0209_BONUS.mp3",

    	"NDE0210A.mp3", "NDE0210B.mp3", "NDE0210C.mp3",

    	"NDE0211A.mp3", "NDE0211B.mp3", "NDE0211C.mp3", 

    	"NDE0212A.mp3", "NDE0212B.mp3", "NDE0212C.mp3", "NDE0212_BONUS.mp3"
	};
	
	private static final String[] QUESTIONS = {
		"NDE0202B_Q1.mp3",

    	"NDE0202B_Q2.mp3", "NDE0202B_Q3.mp3", "NDE0203B_Q1.mp3", "NDE0203B_Q2.mp3",

    	"NDE0203B_Q3.mp3", "NDE0204B_Q1.mp3", "NDE0204B_Q2.mp3", "NDE0204B_Q3.mp3",

    	"NDE0205B_Q1.mp3", "NDE0205B_Q2.mp3", "NDE0205B_Q3.mp3", "NDE0206B_Q1.mp3",

    	"NDE0206B_Q2.mp3", "NDE0206B_Q3.mp3", "NDE0207B_Q1.mp3", "NDE0207B_Q2.mp3",

    	"NDE0207B_Q3.mp3", "NDE0208B_Q1.mp3", "NDE0208B_Q2.mp3", "NDE0208B_Q3.mp3",

    	"NDE0209B_Q1.mp3", "NDE0209B_Q2.mp3", "NDE0209B_Q3.mp3", "NDE0210B_Q1.mp3",

    	"NDE0210B_Q2.mp3", "NDE0210B_Q3.mp3", "NDE0211B_Q1.mp3", "NDE0211B_Q2.mp3",

    	"NDE0211B_Q3.mp3", "NDE0212B_Q1.mp3", "NDE0212B_Q2.mp3", "NDE0212B_Q3.mp3"
	};

//	private static final String[] AVATARS = {
//		"DynEd1Done.3gp", "DynEd2BDone.3gp", "DynEd3ADone.3gp", "DynEd4Done.3gp", "DynEd5Done.3gp", 
//		"DynEd6ADone.3gp", "DynEd7ADone.3gp", 
//	};
	private static final String[] AVATARS = {
		"DynEd3.3gp", "DynEd4.3gp", "DynEd5.3gp", 
		"DynEd6.3gp", "DynEd7.3gp", 
	};

	/**
	 * Creates new object of ContentLoader
	 */
	public ContentLoader() {
	}

	/**
	 * Sets the ProgressListener to this object.
	 * @param listener 
	 */
	public void setListener(ProgressListener listener) {
		this.listener = listener;
	}
	
	public void run() {
		GeneralFunction.createDirectory(Resources.PATH);
		GeneralFunction.createDirectory(Resources.PATH_AUDIO);
		GeneralFunction.createDirectory(Resources.PATH_AUDIO_LEVEL);
		GeneralFunction.createDirectory(Resources.PATH_AUDIO_DIALOGUES);
		GeneralFunction.createDirectory(Resources.PATH_AUDIO_QUESTIONS);
		GeneralFunction.createDirectory(Resources.PATH_AVATAR);
		GeneralFunction.createDirectory(Resources.PATH_AVATAR_EN);

		if(listener != null) {
			listener.onPost("Preparing content..");
		}
		
		totalProgress = DIALOGUES_LESSON_ONE.length + QUESTIONS_LESSON_ONE.length;

		for(int i = 0; i < DIALOGUES_LESSON_ONE.length; i++){
			String dialog = DIALOGUES_LESSON_ONE[i];
			String downloadUrl = Resources.FILES_AUDIO_DIALOGUES + dialog;
			String saveLocation = Resources.PATH_AUDIO_DIALOGUES + dialog;

			DownloadAndSaveFile downloadAndSaveFile = new DownloadAndSaveFile(saveLocation, downloadUrl);
			downloadAndSaveFile.setContentListener(this);
			downloadAndSaveFile.setLabel(dialog);
			threadManager.register(downloadAndSaveFile);
		}

		for(int i = 0; i < QUESTIONS_LESSON_ONE.length; i++) {
			String question = QUESTIONS_LESSON_ONE[i];
			String downloadUrl = Resources.FILES_AUDIO_QUESTIONS + question;
			String saveLocation = Resources.PATH_AUDIO_QUESTIONS + question;

			DownloadAndSaveFile downloadAndSaveFile = new DownloadAndSaveFile(saveLocation, downloadUrl);
			downloadAndSaveFile.setContentListener(this);
			downloadAndSaveFile.setLabel(question);
			threadManager.register(downloadAndSaveFile);
		}

		for(int i = 0; i < DIALOGUES.length; i++) {
			audioDownload.addDialogs(DIALOGUES[i]);
		}

		for(int i = 0; i < QUESTIONS.length; i++) {
			audioDownload.addQuestions(QUESTIONS[i]);
		}
		
		for(int i = 0; i < AVATARS.length; i++) {
			avatarDownload.addAvatars(AVATARS[i]);
		}
		
		if(listener != null){
			listener.onPost("Get ready to download");
		}
		threadManager.start();
		super.run();
	}

	public void onFinishTask(String label) {
		currentProgress++;
		if(listener != null) {
			listener.onProgress(currentProgress, totalProgress);
		}
		if(currentProgress == totalProgress) {
			synchronized (UiApplication.getEventLock()) {
				UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
				audioDownload.start();
				avatarDownload.start();
			}
		}
	}

	public void onErrorTask(String label) {
		onFinishTask(label);
	}
}