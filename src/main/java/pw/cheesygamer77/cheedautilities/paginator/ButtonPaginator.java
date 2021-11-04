package pw.cheesygamer77.cheedautilities.paginator;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pw.cheesygamer77.cheedautilities.ListUtils;
import pw.cheesygamer77.cheedautilities.checks.Check;

import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public void paginate(@NotNull SlashCommandEvent event) {
        ReplyAction ra = event.reply(pages.get(0));
        if(pages.size() > 1) {
            ra = ra.addActionRow(
                    Button.secondary("jumpLeft", Emoji.fromMarkdown("<:jumpleft:899051534044786699>")),
                    Button.primary("left", Emoji.fromMarkdown("<:left:899051552453591072>")),
                    Button.primary("right", Emoji.fromMarkdown("<:right:899051560972197909>")),
                    Button.secondary("jumpRight", Emoji.fromMarkdown("<:jumpright:899051544278876161>")),
                    Button.danger("stop", Emoji.fromMarkdown("<:trashcan:899051569692160002>"))
            );
        }

        init(ra, 0, event);
    }

    public void paginate(@NotNull MessageChannel channel, int pageNum) {
        init(
                channel.sendMessage(pages.get(pageNum))
                        .setActionRow(
                                Button.secondary("jumpLeft", Emoji.fromMarkdown("<:jumpleft:899051534044786699>")),
                                Button.primary("left", Emoji.fromMarkdown("<:left:899051552453591072>")),
                                Button.primary("right", Emoji.fromMarkdown("<:right:899051560972197909>")),
                                Button.secondary("jumpRight", Emoji.fromMarkdown("<:jumpright:899051544278876161>")),
                                Button.danger("stop", Emoji.fromMarkdown("<:trashcan:899051569692160002>"))
                        ),
                pageNum
        );
    }

    public void paginate(@NotNull Message message, int pageNum) {
        init(
                message.editMessage(pages.get(pageNum))
                        .setActionRow(
                                Button.secondary("jumpLeft", Emoji.fromMarkdown("<:jumpleft:899051534044786699>")),
                                Button.primary("left", Emoji.fromMarkdown("<:left:899051552453591072>")),
                                Button.primary("right", Emoji.fromMarkdown("<:right:899051560972197909>")),
                                Button.secondary("jumpRight", Emoji.fromMarkdown("<:jumpright:899051544278876161>")),
                                Button.danger("stop", Emoji.fromMarkdown("<:trashcan:899051569692160002>"))
                        ),
                pageNum
        );
    }

    private void init(@NotNull ReplyAction ra, int pageNum, SlashCommandEvent event) {
        ra.queue(h -> {
            if(pages.size() > 1) {
                pagination(event, pageNum);
            }
            else {
                doneCallback.accept(pages.get(pageNum));
            }
        });
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
                1, TimeUnit.MINUTES, () -> timeoutCallback.accept(message)
        );
    }

    private void pagination(@NotNull SlashCommandEvent event, int pageNum) {
        waiter.waitForEvent(ButtonClickEvent.class,
                e -> checkButtons(e, e.getMessageIdLong()),
                e -> handleButtonClick(e, e.getMessage(), pageNum),
                1, TimeUnit.MINUTES, () -> timeoutCallback.accept(pages.get(pageNum))
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
                event.editMessage(message)
                        .setActionRow(
                                Button.secondary("jumpLeft", Emoji.fromMarkdown("<:jumpleft:899051534044786699>")).asDisabled(),
                                Button.primary("left", Emoji.fromMarkdown("<:left:899051552453591072>")).asDisabled(),
                                Button.primary("right", Emoji.fromMarkdown("<:right:899051560972197909>")).asDisabled(),
                                Button.secondary("jumpRight", Emoji.fromMarkdown("<:jumpright:899051544278876161>")).asDisabled(),
                                Button.danger("stop", Emoji.fromMarkdown("<:trashcan:899051569692160002>")).asDisabled()
                        ).queue();
                return;
        }

        int n = newPageNum;

        event.editMessage(pages.get(newPageNum))
                .setActionRow(
                        Button.secondary("jumpLeft", Emoji.fromMarkdown("<:jumpleft:899051534044786699>")),
                        Button.primary("left", Emoji.fromMarkdown("<:left:899051552453591072>")),
                        Button.primary("right", Emoji.fromMarkdown("<:right:899051560972197909>")),
                        Button.secondary("jumpRight", Emoji.fromMarkdown("<:jumpright:899051544278876161>")),
                        Button.danger("stop", Emoji.fromMarkdown("<:trashcan:899051569692160002>"))
                )
                .queue(m -> pagination(event.getMessage(), n));
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

    /**
     * Returns a pre-built ButtonPaginator builder for paginating through role members
     * @param role The role to paginate the members of
     * @param lineFormatter The function to use to retrieve the format string used for each line in the paginator
     * @param linesPerPage The maximum number of lines on each page
     * @return The newly built ButtonPaginator builder
     */
    public static @NotNull ButtonPaginator.Builder of(@NotNull Role role, Function<Member, String> lineFormatter, int linesPerPage) {
        return of(role.getGuild().getMembersWithRoles(role), lineFormatter, linesPerPage, new MessageBuilder().setEmbeds(
                        new EmbedBuilder()
                                .setTitle("Role Members")
                                .setColor(role.getColor())
                                .setFooter("Role Created At")
                                .setTimestamp(role.getTimeCreated())
                                .build()
                )
                .setContent(role.getId())
                .build());
    }

    public static @NotNull <T> ButtonPaginator.Builder of(
            List<T> list, Function<T, String> lineFormatter, int linesPerPage, Message baseMessage) {
        Builder builder = new Builder();

        // get role members and split it into chunks of equal size
        List<List<T>> chunks = ListUtils.chunkify(list, linesPerPage);

        // build pages for each chunk
        for(List<T> chunk : chunks) {
            // construct each line based off our lineFormatter
            List<String> lines = chunk.stream()
                    .map(lineFormatter)
                    .collect(Collectors.toList());

            // build the page and add it to the paginator
            Message message = new MessageBuilder(baseMessage).setEmbeds(
                    new EmbedBuilder(baseMessage.getEmbeds().get(0))
                            .setDescription(String.join("\n", String.join("\n", lines)))
                            .build()
            ).build();

            builder.addPage(message);
        }

        return builder;
    }

    public static class Builder {
        protected final List<Message> pages = new LinkedList<>();
        protected final ArrayList<Check> checks = new ArrayList<>();
        protected EventWaiter eventWaiter;
        protected final Set<User> users = new HashSet<>();
        protected final Set<Role> roles = new HashSet<>();

        protected Consumer<Message> timeoutCallback = message -> {
            message.editMessage(message).setActionRow(
                    Button.secondary("jumpLeft", Emoji.fromMarkdown("<:jumpleft:899051534044786699>")).asDisabled(),
                    Button.primary("left", Emoji.fromMarkdown("<:left:899051552453591072>")).asDisabled(),
                    Button.primary("right", Emoji.fromMarkdown("<:right:899051560972197909>")).asDisabled(),
                    Button.secondary("jumpRight", Emoji.fromMarkdown("<:jumpright:899051544278876161>")).asDisabled(),
                    Button.danger("stop", Emoji.fromMarkdown("<:trashcan:899051569692160002>")).asDisabled()
            ).queue();
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
            return insertPageAt(!pages.isEmpty() ? pages.size() : 0, message);
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
