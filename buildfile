require 'buildr/git_auto_version'

desc "A simple application demonstrating the use of the gwt-appcache library"
define 'gwt-appcache-example' do
  project.group = 'org.realityforge.gwt.appcache.example'

  compile.options.source = '1.7'
  compile.options.target = '1.7'
  compile.options.lint = 'all'

  compile.with :gwt_appcache_server,
               :gwt_appcache_linker,
               :gwt_appcache_client,
               :javax_servlet,
               :gwt_user,
               :gwt_dev

  gwt_superdev_runner("org.realityforge.gwt.appcache.example.Example",
                      :java_args => ["-Xms512M", "-Xmx1024M", "-XX:PermSize=128M", "-XX:MaxPermSize=256M"],
                      :draft_compile => (ENV["FAST_GWT"] == 'true'),
                      :dependencies => [:javax_validation, :javax_validation_sources] + project.compile.dependencies)
  gwt_dir = gwt(["org.realityforge.gwt.appcache.example.Example"],
                :java_args => ["-Xms512M", "-Xmx1024M", "-XX:PermSize=128M", "-XX:MaxPermSize=256M"],
                :draft_compile => (ENV["FAST_GWT"] == 'true'),
                :dependencies => [:javax_validation, :javax_validation_sources] + project.compile.dependencies)

  package(:war)

  clean { rm_rf "#{File.dirname(__FILE__)}/artifacts" }

  iml.add_gwt_facet({'org.realityforge.gwt.appcache.example.Example' => true},
                    :settings => {:compilerMaxHeapSize => "1024"})

  # Hacke to remove GWT from path
  webroots = {}
  webroots[_(:source, :main, :webapp)] = "/" if File.exist?(_(:source, :main, :webapp))
  assets.paths.each { |path| webroots[path.to_s] = "/" if path.to_s != gwt_dir.to_s }
  iml.add_web_facet(:webroots => webroots)

  iml.add_jruby_facet

  ipr.add_exploded_war_artifact(project,
                                :build_on_make => true,
                                :enable_gwt => true,
                                :enable_war => true,
                                :dependencies => [project, :gwt_cache_filter, :gwt_appcache_server])
end
