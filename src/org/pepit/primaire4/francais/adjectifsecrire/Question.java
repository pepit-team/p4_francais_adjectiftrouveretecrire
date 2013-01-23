package org.pepit.primaire4.francais.adjectifsecrire;

import java.util.ArrayList;

/**
 * Question for the exercise.
 * In fact, a question is just a sentence with an adjective.
 * The real question is, which word in this sentence is the adjective ?
 * 
 * @author jponcy
 * 
 */
public class Question {
	private static ArrayList<Question> questions = null;
	private String question;
	private String ask;

	/*
	 * CONSTRUCTS
	 */
	/**
	 * Default constructor
	 */
	public Question() {
		this(null, null);
	}
	/**
	 * Constructor
	 * @param question
	 * @param ask
	 */
	public Question(String question, String ask) {
		this.question = question;
		this.ask = ask;
		// add to the list
		questions.add(this);
	}

	/*
	 * GETTERS AND SETTERS
	 */
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the ask
	 */
	public String getAsk() {
		return ask;
	}

	/**
	 * @param ask
	 *            the ask to set
	 */
	public void setAsk(String ask) {
		this.ask = ask;
	}

	/*
	 * METHODS
	 */
	/**
	 * Tell if the answer is correct
	 * @param answer
	 * @return
	 */
	public boolean isGoodAnswer(String answer) {
		return ask.equals(answer);
	}
	/**
	 * Get a (random) question without give any information
	 * @return a random question
	 */
	public static Question getRandomQuestion() {
		//check if questions are init
		getQuestions();
		// get a random number
		int rnd = (int) (Math.random() * (questions.size() + 1));
		// return the question
		return questions.get(rnd);
	}

	/**
	 * gives a series of questions 
	 * (the number of questions in the series depends directly on the parameter)
	 * @param nbOfQuestions
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Question> getOneSerieOfQuestions(int nbOfQuestions) throws Exception{
		getQuestions(); // check that the initialization is already done
		int nbQuestions = questions.size();
		// Test if we know questions enough
		if(nbOfQuestions>nbQuestions)
			throw new Exception("Cannot get this number of questions (out of data)");
		else if(nbOfQuestions==nbQuestions)
			return questions;
		else {
			ArrayList<Integer> idQuestionsSelected = new ArrayList<Integer>();
			ArrayList<Question> theReturn = new ArrayList<Question>();
			// get ids of questions
			int temp;
			for(int i=0; i<nbOfQuestions; i++) {
				do { // take a number not already selected
					temp = (int) (Math.random() * (nbQuestions + 1));
				}while(idQuestionsSelected.contains(temp));
				idQuestionsSelected.add(temp);
			}
			// obtain the corresponding questions
			for(Integer id:idQuestionsSelected) {
				theReturn.add(getQuestion(id));
			}
			return theReturn;
		}
	}
	
	/**
	 * Warning about this method the id is just the order of adds in the list
	 * @param id
	 * @return
	 */
	public static Question getQuestion(int id) {
		return questions.get(id);
	}
	
	/**
	 * Get the complete list of questions that the class knows
	 * If the class don't knows question for now, it import a list
	 * (this list is the copy of questions from the exercise in pepit.be)
	 * @return the dictionary of sentences
	 */
	public static ArrayList<Question> getQuestions() {
		if(questions == null) { // If questions is not init then do it
			questions = new ArrayList<Question>();
			// module 1
			questions.add(new Question("La vallée était noyée dans la brume.", "noyée"));
			questions.add(new Question("La porte monumentale s'ouvre sur la plaine.", "monumentale"));
			questions.add(new Question("Elle est mal lunée aujourd'hui.", "lunée"));
			questions.add(new Question("Des vents violents secouent les arbres.", "violents"));
			questions.add(new Question("Il dirige d'importantes usines.", "importantes"));
			questions.add(new Question("Le coton fibre naturelle sert au tissage.", "naturelle"));
			// module 2
			questions.add(new Question("La longue bande de terre jouxte la maison.", "longue"));
			questions.add(new Question("Les invités sont nombreux dans la salle.", "nombreux"));
			questions.add(new Question("Le mois prochain nous déménageons.", "prochain"));
			questions.add(new Question("Je prends le petit morceau de tarte.", "petit"));
			questions.add(new Question("Il porte des chaussures marron.", "marron"));
			questions.add(new Question("Le bal masqué a lieu en ville.", "masqué"));
			// module 3
			questions.add(new Question("Des sons bizarres sortent de l'instrument.", "bizarres"));
			questions.add(new Question("Les traits rouges signalent des fautes.", "rouges"));
			questions.add(new Question("Une pluie abondante ralentit la circulation.", "abondante"));
			questions.add(new Question("C'est une toiture en tuildes vernissées.", "vernissées"));
			questions.add(new Question("Des terres fertiles longent la rivière.", "fertiles"));
			questions.add(new Question("L'atelier est de dimensions imposantes.", "imposantes"));
			// module 4
			questions.add(new Question("D'épais brouillards recouvrent le pays.", "épais"));
			questions.add(new Question("La fauvette niche dans le creux de l'arbre.", "creux"));
			questions.add(new Question("Des murailles solides entourent le château.", "solides"));
			questions.add(new Question("La petite porte permet l'accès aux souterrains.", "petite"));
			questions.add(new Question("L'euro est la monnaie européenne.", "européenne"));
			questions.add(new Question("L'erreur est tragique.", "tragique"));
			// module 5
			questions.add(new Question("Il s'est produit d'étranges phénomènes.", "étranges"));
			questions.add(new Question("Des jeunes gens traînent dans la rue.", "jeunes"));
			questions.add(new Question("L'étroit sentier traversait la forêt.", "étroit"));
			questions.add(new Question("L'accident l'a rendu impotent.", "impotent"));
			questions.add(new Question("La salle est comble ce soir.", "comble"));
			questions.add(new Question("Avez-vous nettoyé les vieux pinceaux.", "vieux"));
		}
		return questions;
	}

	@Override
	public String toString() {
		return String.format("Question: %s, Ask: %s", question, ask);
	}
}
