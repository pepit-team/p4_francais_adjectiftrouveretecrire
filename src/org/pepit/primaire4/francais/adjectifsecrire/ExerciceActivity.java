package org.pepit.primaire4.francais.adjectifsecrire;

import java.util.ArrayList;

import org.pepit.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class ExerciceActivity extends Activity {
	// Constants
	public static final String LITTLE_SETPOINT = "Trouvez l'adjectif de chaque phrase, et écrivez-le dans sa case.";
	public static final String BUTTONS_DEFAULT_VALUE = "phrase suivate";
	public static final int NB_QUESTIONS_IN_ONE_SERIE = 6; // this is the same value that in pepit.be
	// HMI fields
	private TableLayout contentLayout;
	private TableRow actionLayout; // user can really interacts with exercise only here
	private TextView tvQuestion;
	private EditText etAnswer;
	private Button bCommand;
	private TextView tvLittleSetpoint;
	// others
	private Question currentQuestion = null;
	private ArrayList<Question> serieOfQuestions = null;
	private int nbOfWrongAnswers = 0; // the score of the exercise is the numbers of wrongs answers

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// the exercise begin with the setpoint (a little leson about "adjectifs"
		runSetpoint();
	}

	/**
	 * Run the first screen (with setpoint)
	 */
	private void runSetpoint() {
		// init the HMI
		initSetpointHMI();
	}

	/**
	 * Run the exercice
	 */
	private void runExercise() {
		initExerciseHMI();
		// load the serie of questions
		try {
			serieOfQuestions = Question.getOneSerieOfQuestions(NB_QUESTIONS_IN_ONE_SERIE);
			System.out.println("**** We got " + serieOfQuestions.size() + " questions!");
		} catch (Exception e) {e.printStackTrace();	}
		// set the first question
		nextQuestion();
	}

	/**
	 * Initialize the screen to print the setpoint
	 */
	private void initSetpointHMI() {
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		TextView tv = new TextView(this);
		tv.setText(Html.fromHtml("<font color=\"blue\">L'adjectif</font> qualificatif est un mot qui ajouté avant ou après le <font color=\"green\">nom</font> va l'enrichir en lui conférant une autre dimension.<ul><br /><li> - Ex. Une <font color=\"green\">salle (nom)</font> <font color=\"blue\">immense (adjectif)</font>.</li><br />C'est un mot variable, il s'accorde (sauf exception) en genre et en nombre avec le <font color=\"green\">nom</font> ou le <font color=\"green\">ppronom</font> qu'il complète.Note: pour trouver le <font color=\"green\">nom</font> avec lequel il s'accorde on peut poser la question: <font color=\"blue\">Qui est-ce qui ?</font><br /><li>- Ex. C'est un <font color=\"green\">remède</font> <font color=\"blue\">ancestral</font>. - (Qu'est-ce qui est <font color=\"blue\">ancestral</font> ?) un <font color=\"green\">remède</font> (<font color=\"green\">nom, masculin singulier</font>).</li><br /><li>- Ex. Des <font color=\"green\">offres</font> <font color=\"blue\">séduisantes</font>. - (Qui sont <font color=\"blue\">séduisantes</font> ?) des <font color=\"green\">offres</font> (<font color=\"green\">nom, féminin pluriel</font>).</li><br />Il existe aussi des adjectifs non qualificatifs (possessifs, démonstratifs, indéfinis, numéraux, interrogatifs, ou exclamatifs). La nouvelle grammaire préfère en parler comme des déterminants (déterminant démonstratif, possessif, ...).Les principaux déterminants sont les articles, et les adjectifs non qualificatifs.<br /><li>- Ex. adjectis possessifs (mon, ma, notre, leur...), démonstratifs (ce, cet, ces, ...).</li>"));
		Button b = new Button(this);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				runExercise();
			}
		});
		b.setText("Lancer l'exercice");
		ll.addView(tv);
		ll.addView(b);
		setContentView(ll);
	}

	/**
	 * Init The Human Machine Interface
	 * (position of buttons/textfield/inputtextfield) 
	 */
	private void initExerciseHMI() {
		// Layout
		contentLayout = new TableLayout(this);
		contentLayout.setOrientation(LinearLayout.VERTICAL);
		actionLayout = new TableRow(this);
		// Text component
		tvQuestion = new TextView(this); //The value of the text view will be not init here
		// Input text component
		etAnswer = new EditText(this);
		etAnswer.setSingleLine(true);
		etAnswer.setGravity(Gravity.CENTER);
		// button to next question
		bCommand = new Button(this);
		bCommand.setText(BUTTONS_DEFAULT_VALUE);
		bCommand.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				validateAnswerHMI();
			}
		});
		// little setpoint
		tvLittleSetpoint = new TextView(this);
		tvLittleSetpoint.setText(LITTLE_SETPOINT);
		tvLittleSetpoint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		// set positions
		actionLayout.addView(etAnswer, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f)); // fill all the empty space in the line (after the placement of the button)
		actionLayout.addView(bCommand);
		contentLayout.addView(tvLittleSetpoint);
		contentLayout.addView(tvQuestion);
		contentLayout.addView(actionLayout);
		// set the content view
		setContentView(contentLayout);
	}
	
	/**
	 * Test if the answer enter by the user is good or wrong
	 * If it is good, then get the next question
	 */
	private void validateAnswerHMI() {
		String userAnswer = etAnswer.getText().toString();
		if(currentQuestion.isGoodAnswer(userAnswer)) {
			System.out.println("Good !");
			System.out.println("next question");
			// change the question
			nextQuestion();
		} else {
			nbOfWrongAnswers++;
			System.out.println("Too bad ...");
		}
	}

	/**
	 * Change the question in the HMI
	 */
	private void updateQuestion() {
		// update the question
		tvQuestion.setText(currentQuestion.getQuestion());
		// limit the lenght of the answer (this is done in pepit.be ...)
		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(currentQuestion.getAsk().length());
		etAnswer.setFilters(FilterArray);
	}

	/**
	 * @return boolean
	 * @throws Exception 
	 * 
	 */
	private boolean nextQuestion() {
		if(serieOfQuestions==null) return false;
		if(currentQuestion == null)
			currentQuestion = serieOfQuestions.get(0);
		else {
			int nbCurrentQuestion = serieOfQuestions.indexOf(currentQuestion);
			System.out.println("id question = "+nbCurrentQuestion+" size = "+serieOfQuestions.size());
			if(nbCurrentQuestion==(serieOfQuestions.size()-1)) {
				printScore();
				System.out.println("Exercice fini avec " + nbOfWrongAnswers + " mauvaise(s) réponse(s).");
				return false;
			}
			currentQuestion = serieOfQuestions.get(nbCurrentQuestion+1);
		}
		// Set the question in the HMI
		updateQuestion();
		// remove the input text
		etAnswer.setText("");
		return true;
	}
	private void printScore(){
		etAnswer.setVisibility(View.GONE);
		bCommand.setVisibility(View.GONE);
		tvQuestion.setVisibility(View.GONE);
		String messageToPrint;
		switch(nbOfWrongAnswers){
		case 0:
			messageToPrint = "Bravo, vous avez réussi l'exercice sans aucune erreur.";
			break;
		case 1:
			messageToPrint = "Vous avez réussi l'exercice avec 1 seule mauvaise réponse.";
			break;
		default:
			messageToPrint = String.format("Vous avez réussi l'exercice avec %d mauvaises réponses.", nbOfWrongAnswers);
		}
		tvLittleSetpoint.setText(messageToPrint);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_exercice, menu);
		return true;
	}
}
