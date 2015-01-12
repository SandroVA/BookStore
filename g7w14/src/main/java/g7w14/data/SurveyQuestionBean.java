package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Survey question bean that represents the survey question table in the database
 * @author Ian Ozturk
 *
 */

@Named("surveyQuestion")
@SessionScoped
public class SurveyQuestionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3638231807464483441L;

	private long survey_QuestionId;
	private String question;
	private boolean answerSelected = false;

	public boolean isAnswerSelected() {
		return answerSelected;
	}
	
	public void setAnswerSelected(boolean answerSelected) {
		this.answerSelected = answerSelected;
	}
	
	public long getSurvey_QuestionId() {
		return survey_QuestionId;
	}

	public void setSurvey_QuestionId(long survey_QuestionId) {
		this.survey_QuestionId = survey_QuestionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((question == null) ? 0 : question.hashCode());
		result = prime * result
				+ (int) (survey_QuestionId ^ (survey_QuestionId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SurveyQuestionBean other = (SurveyQuestionBean) obj;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (survey_QuestionId != other.survey_QuestionId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SurveyQuestionBean [survey_QuestionId=" + survey_QuestionId
				+ ", question=" + question + "]";
	}

	

}
