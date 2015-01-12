package g7w14.data;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Survey Bean that represents the survey answer table in the database
 * @author Ian Ozturk
 *
 */

@Named("surveyAnswer")
@SessionScoped
public class SurveyAnswerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7241608518680299652L;

	private long survey_AnswersId;
	private long survey_QuestionId;
	private String answer_Text;
	private int answer_count;
	
	public long getSurvey_AnswersId() {
		return survey_AnswersId;
	}
	
	public void setSurvey_AnswersId(long survey_AnswersId) {
		this.survey_AnswersId = survey_AnswersId;
	}

	public long getSurvey_QuestionId() {
		return survey_QuestionId;
	}

	public void setSurvey_QuestionId(long survey_QuestionId) {
		this.survey_QuestionId = survey_QuestionId;
	}

	public String getAnswer_Text() {
		return answer_Text;
	}

	public void setAnswer_Text(String answer_Text) {
		this.answer_Text = answer_Text;
	}

	public int getAnswer_count() {
		return answer_count;
	}

	public void setAnswer_count(int answer_count) {
		this.answer_count = answer_count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((answer_Text == null) ? 0 : answer_Text.hashCode());
		result = prime * result + answer_count;
		result = prime * result
				+ (int) (survey_AnswersId ^ (survey_AnswersId >>> 32));
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
		SurveyAnswerBean other = (SurveyAnswerBean) obj;
		if (answer_Text == null) {
			if (other.answer_Text != null)
				return false;
		} else if (!answer_Text.equals(other.answer_Text))
			return false;
		if (answer_count != other.answer_count)
			return false;
		if (survey_AnswersId != other.survey_AnswersId)
			return false;
		if (survey_QuestionId != other.survey_QuestionId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SurveyAnswerBean [survey_AnswersId=" + survey_AnswersId
				+ ", survey_QuestionId=" + survey_QuestionId + ", answer_Text="
				+ answer_Text + ", answer_count=" + answer_count + "]";
	}

}
