package com.mrpowergamerbr.loritta.commands.vanilla.social

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mrpowergamerbr.loritta.commands.AbstractCommand
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.locale.BaseLocale
import com.mrpowergamerbr.loritta.utils.loritta

class SobreMimCommand : AbstractCommand("aboutme", listOf("sobremim"), CommandCategory.SOCIAL) {
    override fun getUsage(): String {
        return "<nova mensagem>"
    }

    override fun getDescription(locale: BaseLocale): String {
        return locale["SOBREMIM_DESCRIPTION"]
    }

    override fun run(context: CommandContext, locale: BaseLocale) {
        var profile = context.lorittaUser.profile;
        if (context.args.size > 0) {
            profile.aboutMe = context.args.joinToString(" ")
            context.sendMessage(context.getAsMention(true) + context.locale["SOBREMIM_CHANGED", profile.aboutMe])
            loritta.usersColl.updateOne(
                    Filters.eq("_id", profile.userId),
                    Updates.set("aboutMe", profile.aboutMe)
            )
        } else {
            this.explain(context);
        }
    }
}