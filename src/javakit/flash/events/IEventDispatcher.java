package javakit.flash.events;

public interface IEventDispatcher
{
	/** 使用 EventDispatcher 对象注册事件侦听器对象，以使侦听器能够接收事件通知。可以为特定类型的事件、阶段和优先级在显示列表的所有节点上注册事件侦听器。<br><br>
	 * 成功注册一个事件侦听器后，无法通过额外调用 addEventListener() 来更改其优先级。<br>
	 * 要更改侦听器的优先级，必须先调用 removeEventListener()。然后，可以使用新的优先级再次注册该侦听器。<br>
	 * 注册该侦听器后，如果继续调用具有不同 type 值的 addEventListener()，则会创建单独的侦听器注册。<br>
	 * 如果不再需要某个事件侦听器，可调用 EventDispatcher.removeEventListener() 删除它；否则会产生内存问题。<br>
	 * 由于垃圾回收器不会删除仍包含引用的对象，因此不会从内存中自动删除使用已注册事件侦听器的对象。<br>
	 * 复制 EventDispatcher 实例时并不复制其中附加的事件侦听器。（如果新近创建的节点需要一个事件侦听器，必须在创建该节点后附加该侦听器。）<br>
	 * 但是，如果移动 EventDispatcher 实例，则其中附加的事件侦听器也会随之移动。<br> */
	public void addEventListener(String type, Class<?> funcAtClass, String funcName, Class<? extends Event> funcParamClazz, Object owner, int priority, boolean useWeakReference);
	
	/** 将事件调度到事件流中。事件目标是对其调用 dispatchEvent() 的 EventDispatcher 对象。 */
	public boolean dispatchEvent(Event event);
	
	/** 检查 EventDispatcher 对象是否为特定事件类型注册了任何侦听器。<br><br>
	 * 这样，您就可以确定 EventDispatcher 对象在事件流层次结构中的哪个位置改变了对事件类型的处理。<br>
	 * 要确定特定事件类型是否确实会触发事件侦听器，请使用 IEventDispatcher.willTrigger()。 <br>
	 * hasEventListener() 与 willTrigger() 的区别是：hasEventListener() 只检查它所属的对象，而 willTrigger() 检查整个事件流以查找由 type 参数指定的事件。 */
	public boolean hasEventListener(String type);
	
	/** 从 EventDispatcher 对象中删除侦听器。<br><br>
	 * 如果没有向 EventDispatcher 对象注册任何匹配的侦听器，则对此方法的调用没有任何效果。 */
	public void removeEventListener(String type, Class<?> funcAtClass, String funcName, Object owner);

}
