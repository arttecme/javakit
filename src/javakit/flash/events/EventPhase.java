package javakit.flash.events;

public class EventPhase
{
	/** 捕获阶段，是事件流的第一个阶段。 */
	public static final byte CAPTURING_PHASE = 1;
	/** 目标阶段，是事件流的第二个阶段。 */
	public static final byte AT_TARGET = 2;
	/** 冒泡阶段，是事件流的第三个阶段。 */
	public static final byte BUBBLING_PHASE = 3;
}
