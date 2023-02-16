package com.github.io.mangjoo;

import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.update.ChannelUpdateArchivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.channel.concrete.ThreadChannelImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ForumListener extends ListenerAdapter {
    private final Long studyForumId;
    private final Long studyCategoryId;

    public ForumListener(Long studyForumId, Long studyCategoryId) {
        this.studyForumId = studyForumId;
        this.studyCategoryId = studyCategoryId;
    }

    @Override
    public void onChannelCreate(@NotNull ChannelCreateEvent event) {

        try {
            if (event.getChannel().asThreadChannel().getParentChannel().asForumChannel()
                    .getIdLong() == studyForumId) {
                Category categoryById = event.getJDA().getCategoryById(studyCategoryId);
                Objects.requireNonNull(categoryById).createVoiceChannel("[스터디] " + event.getChannel().getName()).queue();
            }
        } catch (Exception ignored) {

        }
    }

    @Override
    public void onChannelUpdateArchived(@NotNull ChannelUpdateArchivedEvent event) {
        try {
            if (isForum(event) && isArchaive(event)) {
                Objects.requireNonNull(event.getJDA().getCategoryById(studyCategoryId))
                        .getVoiceChannels().stream()
                        .filter(voiceChannel -> voiceChannel.getName().equals("[스터디] " + event.getChannel().getName()))
                        .forEach(channel -> channel.delete().queue());
            }
            if (isForum(event) && !isArchaive(event)) {
                Category categoryById = event.getJDA().getCategoryById(studyCategoryId);
                Objects.requireNonNull(categoryById).createVoiceChannel("[스터디] " + event.getChannel().getName()).queue();
            }
        } catch (Exception ignored) {
        }
    }

    private boolean isArchaive(@NotNull ChannelUpdateArchivedEvent event) {
        return ((ThreadChannelImpl) event.getChannel()).isArchived();
    }

    private boolean isForum(@NotNull ChannelUpdateArchivedEvent event) {
        return event.getChannel().asThreadChannel().getParentChannel().asForumChannel().getIdLong() == studyForumId;
    }


}