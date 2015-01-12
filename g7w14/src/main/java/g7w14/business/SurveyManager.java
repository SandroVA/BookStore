package g7w14.business;

import g7w14.data.SurveyAnswerBean;
import g7w14.data.SurveyQuestionBean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 * Survey action bean
 * @author Ian Ozturk
 *
 */

@Named("surveyManager")
@RequestScoped
public class SurveyManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6744446413206373513L;

	@Inject
	SurveyQuestionBean sqb;
	@Inject
	SurveyAnswerBean sab;
	@Resource(name = "jdbc/g7w14")
	private DataSource ds;

	/**
	 * Gets a random survey question
	 * @author Ian Ozturk
	 * @return question Array list of SurveyQuestionBean's
	 * @throws SQLException
	 */
	public ArrayList<SurveyQuestionBean> getSurveyQuestion()
			throws SQLException {
		ArrayList<SurveyQuestionBean> question = new ArrayList<SurveyQuestionBean>();

		String sql = "select * from survey_questions order by RAND() LIMIT 1";

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);
				ResultSet resultSet = pStatement.executeQuery()) {
			while (resultSet.next()) {

				sqb.setSurvey_QuestionId(resultSet.getLong("Survey_QuestionId"));
				sqb.setQuestion(resultSet.getString("Question"));

				question.add(sqb);
			}

		}

		return question;
	}

	/**
	 * Gets the appropriate answers based on the randomly selected question's ID
	 * @author Ian Ozturk
	 * @return answers Array list of SurveyAnwerBean's
	 * @throws SQLException
	 */
	public ArrayList<SurveyAnswerBean> getAnswers() throws SQLException {
		ArrayList<SurveyAnswerBean> answers = new ArrayList<SurveyAnswerBean>();

		String sql = "select * from survey_answers where Survey_QuestionId = ?";

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			pStatement.setLong(1, sqb.getSurvey_QuestionId());

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					SurveyAnswerBean answer = new SurveyAnswerBean();

					answer.setSurvey_AnswersId(resultSet
							.getLong("Survey_AnswersId"));
					answer.setSurvey_QuestionId(resultSet
							.getLong("Survey_QuestionId"));
					answer.setAnswer_Text(resultSet.getString("Answer_Text"));
					answer.setAnswer_count(resultSet.getInt("answer_count"));

					answers.add(answer);
				}
			}
		}

		return answers;
	}
	
	/**
	 * Updates the number of times an answer has been picked
	 * @author Ian Ozturk
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	public String updateCount() throws SQLException {
		int result = 0;
		String sql = "UPDATE survey_answers SET answer_count = answer_count + 1 where Survey_AnswersId = ?";


		
		try (Connection conn = ds.getConnection();

		PreparedStatement pStatement = conn.prepareStatement(sql)) {
			pStatement.setLong(1, sab.getSurvey_AnswersId());
			result = pStatement.executeUpdate();

		}
		sqb.setAnswerSelected(true);
		return null;
	}

}
