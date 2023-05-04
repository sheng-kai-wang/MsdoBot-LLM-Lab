package ntou.soselab.msdobot_llm_lab.Service.DiscordService;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import ntou.soselab.msdobot_llm_lab.Service.NLPService.DialogueTracker;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscordSlashCommandListener extends ListenerAdapter {

    private DialogueTracker dialogueTracker;

    @Autowired
    public DiscordSlashCommandListener(DialogueTracker dialogueTracker) {
        this.dialogueTracker = dialogueTracker;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        System.out.println(">>> trigger slash command event");

        //        if (event.isFromGuild()) return;
        String eventName = event.getName();
        User user = event.getUser();
        String userId = user.getId();
        String userName = user.getName();
        System.out.println("[DEBUG] slash command " + eventName);
        System.out.println("[User ID] " + userId);
        System.out.println("[User Name] " + userName);

        if ("lab_start".equals(eventName)) {
            MessageCreateData response = dialogueTracker.addTester(userId, userName);
            event.reply(response).queue();
        }

        if ("lab_end".equals(eventName)) {
            MessageCreateData response = dialogueTracker.removeTester(userId, userName);
            event.reply(response).queue();
        }

        System.out.println("<<< end of current slash command event");
        System.out.println();
    }
}
