

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

import org.tzi.use.runtime.IPlugin;
import org.tzi.use.runtime.IPluginRuntime;
import org.tzi.use.runtime.gui.IPluginActionDelegate;
import org.tzi.use.runtime.gui.IPluginAction;

public class ChatGPTPlugin implements IPlugin, IPluginActionDelegate {
    private IPluginRuntime pluginRuntime;

    //@Override
    public void run(IPluginRuntime pluginRuntime) throws Exception {
      this.pluginRuntime = pluginRuntime;
      Chat chat = new Chat();
      chat.main(new String[]{});
    }

    // @Override
    public String getName() {
        return "ChatGPT";
    }

    // @Override
    public String getVersion() {
        return "1.1";
    }

    //  @Override
    public String getAuthor() {
        return "Alexis Huante, Vilen Elliott";
    }

    //  @Override
    public String getDescription() {
        return "ChatGPT Plugin for USE";
    }

    public class Chat {

        private static StringBuilder conversationHistory = new StringBuilder();
        public String ChatGPT(String text) throws Exception {

            conversationHistory.append(text).append("\n"); // REMEMBERS THE QUESTION FROM USER FROM PREVIOUS CONVERSATIONS.

            String url = "https://api.openai.com/v1/completions";
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer [insert key here and remove brackets]");
            
            JSONObject data = new JSONObject();
            data.put("model", "text-davinci-003");
            data.put("prompt", conversationHistory.toString()); //THIS MAKES CHATGPT REMEMBER PREVIOUS CONVERSATIONS
            data.put("max_tokens", 300);
            data.put("temperature", 0.9);
            data.put("top_p", 1.0);
            data.put("frequency_penalty", 0.0);
            data.put("presence_penalty", 0.6);

            con.setDoOutput(true);
            con.getOutputStream().write(data.toString().getBytes());

            String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                    .reduce((a, b) -> a + b).get();


            String answer = new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text");

            conversationHistory.append(answer).append("\n"); // REMEMBERS THE ANSWER FROM PREVIOUS CONVERSATIONS
            return answer;

        }

        public void main(String[] args) {
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

    @Override
    public void performAction(IPluginAction pluginAction) {
        try {
           // run(pluginRuntime); this will run the program 2 times, we don't want that
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



/*

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

import org.tzi.use.runtime.IPlugin;
import org.tzi.use.runtime.IPluginRuntime;
import org.tzi.use.runtime.gui.IPluginActionDelegate;
import org.tzi.use.runtime.gui.IPluginAction;

import org.tzi.use.runtime.IPluginRuntime;
import org.tzi.use.runtime.impl.Plugin;

public class ChatGPTPlugin implements IPlugin, IPluginActionDelegate {
    private IPluginRuntime pluginRuntime;
    private Chat chat;



    //@Override
    public void init(IPluginRuntime pluginRuntime) throws Exception {
        this.pluginRuntime = pluginRuntime;
        chat = new Chat();
        chat.main(new String[]{});
    }
    //@Override
    public void run(IPluginRuntime pluginRuntime) throws Exception {
        chat = new Chat();
        chat.main(new String[]{});
    }

    // @Override
    public String getName() {
        return "ChatGPT";
    }

    // @Override
    public String getVersion() {
        return "1.1";
    }

    //  @Override
    public String getAuthor() {
        return "Alexis Huante, Vilen Elliott";
    }

    //  @Override
    public String getDescription() {
        return "ChatGPT Plugin for USE";
    }

    public class Chat {
        public String ChatGPT(String text) throws Exception {
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
            // ... (ChatGPT method code)

        }

        public void main(String[] args) {
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

    @Override
    public void performAction(IPluginAction pluginAction) {
        try {
            run(pluginRuntime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
*/


