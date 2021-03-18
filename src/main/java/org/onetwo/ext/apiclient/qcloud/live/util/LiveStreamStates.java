package org.onetwo.ext.apiclient.qcloud.live.util;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * active：活跃，
inactive：非活跃，
forbid：禁播。
 * @author weishao zeng
 * <br/>
 */
@AllArgsConstructor
public enum LiveStreamStates {

	ACTIVE("活跃"),
	INACTIVE("非活跃"),
	FORBID("禁播");
	
	@Getter
	private final String label;
	

    static public LiveStreamStates of(String name) {
        return Stream.of(values()).filter(s -> s.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("error state: " + name));
    }
}

