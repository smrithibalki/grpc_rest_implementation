package com.smrithi.protobuf.hello

object LogCheckGrpc {
  val METHOD_EVALUATE: _root_.io.grpc.MethodDescriptor[com.smrithi.protobuf.hello.Input, com.smrithi.protobuf.hello.Response] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("LogCheck", "Evaluate"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[com.smrithi.protobuf.hello.Input])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[com.smrithi.protobuf.hello.Response])
      .setSchemaDescriptor(_root_.scalapb.grpc.ConcreteProtoMethodDescriptorSupplier.fromMethodDescriptor(com.smrithi.protobuf.hello.HelloProto.javaDescriptor.getServices().get(0).getMethods().get(0)))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("LogCheck")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(com.smrithi.protobuf.hello.HelloProto.javaDescriptor))
      .addMethod(METHOD_EVALUATE)
      .build()
  
  trait LogCheck extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = LogCheck
    def evaluate(request: com.smrithi.protobuf.hello.Input): scala.concurrent.Future[com.smrithi.protobuf.hello.Response]
  }
  
  object LogCheck extends _root_.scalapb.grpc.ServiceCompanion[LogCheck] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[LogCheck] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.smrithi.protobuf.hello.HelloProto.javaDescriptor.getServices().get(0)
    def scalaDescriptor: _root_.scalapb.descriptors.ServiceDescriptor = com.smrithi.protobuf.hello.HelloProto.scalaDescriptor.services(0)
    def bindService(serviceImpl: LogCheck, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
      _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
      .addMethod(
        METHOD_EVALUATE,
        _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[com.smrithi.protobuf.hello.Input, com.smrithi.protobuf.hello.Response] {
          override def invoke(request: com.smrithi.protobuf.hello.Input, observer: _root_.io.grpc.stub.StreamObserver[com.smrithi.protobuf.hello.Response]): _root_.scala.Unit =
            serviceImpl.evaluate(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
              executionContext)
        }))
      .build()
  }
  
  trait LogCheckBlockingClient {
    def serviceCompanion = LogCheck
    def evaluate(request: com.smrithi.protobuf.hello.Input): com.smrithi.protobuf.hello.Response
  }
  
  class LogCheckBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[LogCheckBlockingStub](channel, options) with LogCheckBlockingClient {
    override def evaluate(request: com.smrithi.protobuf.hello.Input): com.smrithi.protobuf.hello.Response = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_EVALUATE, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): LogCheckBlockingStub = new LogCheckBlockingStub(channel, options)
  }
  
  class LogCheckStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[LogCheckStub](channel, options) with LogCheck {
    override def evaluate(request: com.smrithi.protobuf.hello.Input): scala.concurrent.Future[com.smrithi.protobuf.hello.Response] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_EVALUATE, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): LogCheckStub = new LogCheckStub(channel, options)
  }
  
  def bindService(serviceImpl: LogCheck, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition = LogCheck.bindService(serviceImpl, executionContext)
  
  def blockingStub(channel: _root_.io.grpc.Channel): LogCheckBlockingStub = new LogCheckBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): LogCheckStub = new LogCheckStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.smrithi.protobuf.hello.HelloProto.javaDescriptor.getServices().get(0)
  
}