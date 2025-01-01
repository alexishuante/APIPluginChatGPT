
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONObject;

public class Chat {
    public static String ChatGPT(String text) throws Exception {
        String url = "https://api.openai.com/v1/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        JSONObject data = new JSONObject();
        data.put("model", "text-davinci-003");
        data.put("prompt", text);
        data.put("max_tokens", 300);
        data.put("temperature", 0.9);
        data.put("top_p", 1.0);
        data.put("frequency_penalty", 0.0);
        data.put("presence_penalty", 0.6);

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                .reduce((a, b) -> a + b).get();

        return new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ChatGPT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        JPanel panel = new JPanel();
        frame.add(panel);

        JLabel questionLabel = new JLabel("Enter your question:");
        panel.add(questionLabel);

        JTextField questionField = new JTextField(40);
        panel.add(questionField);

        JTextArea answerArea = new JTextArea(6, 40);
        answerArea.setLineWrap(true);
        answerArea.setWrapStyleWord(true);
        answerArea.setEditable(false);
        panel.add(answerArea);

        JButton submitButton = new JButton("Submit");
        panel.add(submitButton);

        JButton speechButton = new JButton("Speech To Text");
        panel.add(speechButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String question = questionField.getText();
                try {
                    String answer = ChatGPT(question);
                    answerArea.setText(answer);
                } catch (Exception ex) {
                    answerArea.setText("Error: Unable to get a response from ChatGPT.");
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }
}
