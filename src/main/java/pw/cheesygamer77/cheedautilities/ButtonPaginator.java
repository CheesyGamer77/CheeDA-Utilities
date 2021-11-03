package pw.cheesygamer77.cheedautilities;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pw.cheesygamer77.cheedautilities.checks.Check;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ButtonPaginator {
    protected final EventWaiter waiter;
    protected final List<Message> pages;
    protected final Consumer<Message> timeoutCallback;
    protected final Consumer<Message> checkFailureCallback;
    protected final Consumer<Message> doneCallback;
    protected final Set<User> users;
    protected final Set<Role> roles;

    ButtonPaginator(EventWaiter waiter, List<Message> messages, Consumer<Message> timeoutCallback,
    Consumer<Message> checkFailureCallback, Consumer<Message> doneCallback, Set<User> validUsers, Set<Role> validRoles) {
        this.waiter = waiter;
        this.pages = messages;
        this.timeoutCallback = timeoutCallback;
        this.checkFailureCallback = checkFailureCallback;
        this.doneCallback = doneCallback;
        this.users = validUsers;
        this.roles = validRoles;
    }

    public void paginate(MessageChannel channel) {
        paginate(channel, 0);
    }

    public void paginate(@NotNull MessageChannel channel, int pageNum) {
        init(
                channel.sendMessage(pages.get(pageNum))
                        .setActionRow(
                                Button.secondary("jumpLeft", Emoji.fromMarkdown(":track_previous:")),
                                Button.primary("left", Emoji.fromMarkdown(":rewind:")),
                                Button.danger("stop", Emoji.fromMarkdown(":wastebasket:")),
                                Button.primary("right", Emoji.fromMarkdown(":fast_forward:")),
                                Button.secondary("jumpRight", Emoji.fromMarkdown(":track_next:"))
                        ),
                pageNum
        );
    }

    public void paginate(@NotNull Message message, int pageNum) {
        init(message.editMessage(pages.get(pageNum)), pageNum);
    }

    private void init(@NotNull RestAction<Message> ra, int pageNum) {
        ra.queue(message -> {
            if(pages.size() > 1) {
                pagination(message, pageNum);
            }
            else {
                doneCallback.accept(message);
            }
        });
    }

    private void pagination(Message message, int pageNum) {
        waiter.waitForEvent(ButtonClickEvent.class,
                event -> checkButtons(event, message.getIdLong()),
                event -> handleButtonClick(event, message, pageNum),
                1, TimeUnit.MINUTES, () -> doneCallback.accept(message)
        );
    }

    private void handleButtonClick(@NotNull ButtonClickEvent event, Message message, int pageNum) {
        int newPageNum = pageNum;

        switch(event.getComponentId()) {
            case "left":
                if(pageNum == 0)
                    // wrap around
                    newPageNum = pages.size();

                if(newPageNum > 0)
                    newPageNum -= 1;

                break;
            case "right":
                if(pageNum == pages.size() - 1)
                    // wrap around
                    newPageNum = 0;

                if(newPageNum < pages.size() - 1)
                    newPageNum += 1;

                break;
            case "jumpLeft":
                newPageNum = 0;
                break;
            case "jumpRight":
                newPageNum = pages.size() - 1;
                break;
            case "stop":
                doneCallback.accept(message);
                return;
        }

        int n = newPageNum;
        message.editMessage(pages.get(newPageNum)).queue(m -> pagination(m, n));
    }

    private boolean checkButtons(@NotNull ButtonClickEvent event, long messageID) {
        if(event.getMessage().getIdLong() != messageID)
            return false;


        return isValidUser(event.getUser(), event.getGuild());
    }

    private boolean isValidUser(@NotNull User user, @Nullable Guild guild) {
        if(user.isBot())
            return false;
        if(users.isEmpty() && roles.isEmpty())
            return false;
        if(users.contains(user))
            return true;
        if(guild == null || !guild.isMember(user))
            return false;

        return Objects.requireNonNull(guild.getMember(user)).getRoles().stream().anyMatch(roles::contains);
    }

    public static class Builder {
        protected final List<Message> pages = new LinkedList<>();
        protected final ArrayList<Check> checks = new ArrayList<>();
        protected EventWaiter eventWaiter = new EventWaiter();
        protected final Set<User> users = new HashSet<>();
        protected final Set<Role> roles = new HashSet<>();

        protected Consumer<Message> timeoutCallback = message -> {
            message.getChannel()
                    .sendMessage("Timedout")
                    .queue();
        };

        protected Consumer<Message> checkFailureCallback = message -> {
            message.getChannel()
                    .sendMessage("Check failure")
                    .queue();
        };

        protected Consumer<Message> doneCallback = message -> {};


        public Builder insertPageAt(int i, Message message) {
            pages.add(i, message);
            return this;
        }

        public Builder prependPage(Message message) {
            return insertPageAt(0, message);
        }

        public Builder addPage(Message message) {
            return insertPageAt(!pages.isEmpty() ? pages.size() - 1 : 0, message);
        }

        public Builder setTimeoutCallback(Consumer<Message> callback) {
            timeoutCallback = callback;
            return this;
        }

        public Builder setCheckFailureCallback(Consumer<Message> callback) {
            checkFailureCallback = callback;
            return this;
        }

        public Builder setDoneCallback(Consumer<Message> callback) {
            doneCallback = callback;
            return this;
        }

        public Builder addCheck(Check check) {
            checks.add(check);
            return this;
        }

        public Builder setEventWaiter(EventWaiter waiter) {
            eventWaiter = waiter;
            return this;
        }

        public Builder setValidUsers(Set<User> users) {
            this.users.addAll(users);
            return this;
        }

        public Builder setValidRoles(Set<Role> roles) {
            this.roles.addAll(roles);
            return this;
        }

        public ButtonPaginator build() {
            return new ButtonPaginator(eventWaiter, pages, timeoutCallback, checkFailureCallback, doneCallback, users, roles);
        }
    }
}
