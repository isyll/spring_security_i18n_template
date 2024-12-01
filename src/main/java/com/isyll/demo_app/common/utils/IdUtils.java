package com.isyll.demo_app.common.utils;

import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

public class IdUtils {

	public static String convertUuidToBaseId(UUID uuid) {
		return FriendlyId.toFriendlyId(uuid);
	}

	public static UUID convertBaseIdToUuid(String baseId) {
		return FriendlyId.toUuid(baseId);
	}
}
